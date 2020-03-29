package org.covital.measurements.presentation

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_measure.progress_bar
import org.covital.R
import org.covital.databinding.FragmentMeasureBinding
import org.covital.measurements.presentation.measurements.domain.O2Analyzer
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.Executor

class MeasureFragment : Fragment() {
    private val sharedViewModel: MeasurementsViewModel by sharedViewModel()
    private val viewModel: MeasureViewModel by lifecycleScope.viewModel(this) {
        parametersOf(sharedViewModel)
    }

    private var onAnalyzerResult: (Int) -> Unit = { viewModel.onAnalyzerResult(it) }
    private var onAnalyzerProgress: (Int) -> Unit = { updateProgress(it) }
    private var analyzer: O2Analyzer? = null

    private lateinit var executor: Executor
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
    }

    override fun onResume() {
        super.onResume()
        resetState()
    }

    private fun resetState() {
        viewModel.resetState()
        analyzer?.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMeasureBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_measure, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.recording.observe(viewLifecycleOwner, Observer {
            if (it) onStartRecording()
            else resetCamera()
        })

        return binding.root
    }

    private fun resetCamera() {
        camera?.cameraControl?.enableTorch(false)
        camera = null
        cameraProvider?.unbindAll()
        cameraProvider = null
    }

    private fun onStartRecording() {
        updateProgress(0)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(640, 480))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            analyzer?.onDestroy()
            val newAnalyzer = O2Analyzer(onAnalyzerResult, onAnalyzerProgress)
            analyzer = newAnalyzer
            imageAnalysis.setAnalyzer(executor, newAnalyzer)

            cameraProvider?.unbindAll()
            camera = cameraProvider?.bindToLifecycle(this, cameraSelector, imageAnalysis, preview)
            camera?.cameraControl?.enableTorch(true)
        }, executor)
    }

    private fun updateProgress(progress: Int) {
        progress_bar?.progress = progress
    }
}