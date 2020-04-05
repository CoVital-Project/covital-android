package org.covital.measurements.presentation.measurements.domain

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import org.covital.measurements.presentation.measurements.domain.math.Fft
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.ceil
import kotlin.math.sqrt

private const val Y_PLANE = 0
private const val U_PLANE = 1
private const val V_PLANE = 2

private const val TOTAL_SECONDS = 30
class O2Analyzer(
    private var onResult: ((Int) -> Unit)?,
    private var onProgress: ((Int) -> Unit)?
) : ImageAnalysis.Analyzer {
    private val processing = AtomicBoolean(true)

    private var counter = 0
    private var stdRed = 0.0
    private var stdBlue = 0.0
    private var sumRed = 0.0
    private var sumBlue = 0.0
    private var o2 = 0

    private val redAvgList = mutableListOf<Double>()
    private val blueAvgList = mutableListOf<Double>()

    private var startTime: Long = 0
    private var samplingFreq = 0.0

    private var currSec = 0
    override fun analyze(image: ImageProxy) {
        if (startTime == 0L) startTime = System.currentTimeMillis()
        if (!processing.get()) return

        if ((System.currentTimeMillis() - startTime) / 1000 > currSec) {
            currSec++
            onProgress?.invoke(currSec * 100 / TOTAL_SECONDS)
        }

        val (redAvg, blueAvg) = image.getAverageRedAndBlue()
        sumRed += redAvg
        sumBlue += blueAvg
        redAvgList.add(redAvg)
        blueAvgList.add(blueAvg)
        counter++

        val endTime = System.currentTimeMillis()
        val totalTimeInSecs: Double = (endTime - startTime) / 1000.0
        if (totalTimeInSecs >= TOTAL_SECONDS) handleEnd(totalTimeInSecs)

        image.close()
    }

    private fun handleEnd(totalTimeInSecs: Double) {
        startTime = System.currentTimeMillis()
        samplingFreq = counter / totalTimeInSecs

        val red: Array<Double> = redAvgList.toTypedArray()
        val blue: Array<Double> = blueAvgList.toTypedArray()
        val hRFreq: Double = Fft.FFT(red, counter, samplingFreq)
        val bpm: Double = ceil(hRFreq * 60)
        val meanRed: Double = sumRed / counter
        val meanBlue: Double = sumBlue / counter

        for (i in 0 until counter - 1) {
            val bufferBlue: Double = blue[i]
            stdBlue += (bufferBlue - meanBlue) * (bufferBlue - meanBlue)
            val bufferRed: Double = red[i]
            stdRed += (bufferRed - meanRed) * (bufferRed - meanRed)
        }

        val varRed = sqrt(stdRed / (counter - 1))
        val varBlue = sqrt(stdBlue / (counter - 1))
        val r: Double = varRed / meanRed / (varBlue / meanBlue)

        val spo2: Double = 100 - 5 * r
        o2 = spo2.toInt()

        if (o2 < 80 || o2 > 99 || bpm < 45 || bpm > 200) {
            startTime = System.currentTimeMillis()
            counter = 0
            processing.set(false)
            onResult?.invoke(0)
        } else {
            processing.set(false)
            onResult?.invoke(o2)
        }
    }

    private fun ImageProxy.getAverageRedAndBlue(): Pair<Double, Double> {
        val yData = planes[Y_PLANE].buffer.toByteArray()
        val uData = planes[U_PLANE].buffer.toByteArray()
        val vData = planes[V_PLANE].buffer.toByteArray()

        val yPixelStride = planes[Y_PLANE].pixelStride
        val yRowStride = planes[Y_PLANE].rowStride
        val uPixelStride = planes[U_PLANE].pixelStride
        val uRowStride = planes[U_PLANE].rowStride
        val vPixelStride = planes[V_PLANE].pixelStride
        val vRowStride = planes[V_PLANE].rowStride

        var redSum = 0.0
        var blueSum = 0.0
        var greenSum = 0.0
        for (x in 0 until width) {
            for (y in 0 until height) {
                val yPixel = yData[y * yRowStride + x * yPixelStride].toInt() and 255
                val uPixel = (uData[(y/2 * uRowStride + x/2 * uPixelStride)].toInt() and 255) - 128
                val vPixel = (vData[(y/2 * vRowStride + x/2 * vPixelStride)].toInt() and 255) - 128

                redSum += yPixel + (1.370705 * vPixel)
                greenSum += yPixel - (0.698001 * vPixel) - (0.337633 * uPixel)
                blueSum += yPixel + (1.732446 * uPixel)
            }
        }
        val numberOfPixels = width * height

        return Pair(redSum / numberOfPixels, blueSum / numberOfPixels)
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    fun onDestroy() {
        onResult = null
        onProgress = null
    }
}
