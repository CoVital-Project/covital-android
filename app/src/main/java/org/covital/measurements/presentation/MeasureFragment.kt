package org.covital.measurements.presentation

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_measure.progress_bar
import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentMeasureBinding
import org.covital.measurements.presentation.measurements.domain.O2Analyzer
import org.covital.sharedGraphViewModel
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.Executor

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO
)

class MeasureFragment : BaseFragment<FragmentMeasureBinding>() {

    override val layoutRes: Int = R.layout.fragment_measure

    private val sharedViewModel: MeasurementsViewModel by sharedGraphViewModel(R.id.measure_graph)
    private val viewModel: MeasureViewModel by lifecycleScope.viewModel(this) {
        parametersOf(sharedViewModel)
    }

    override fun setupBinding(binding: FragmentMeasureBinding) {
        binding.viewModel = viewModel
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

    private fun requestCameraPermissions() {
        if (allPermissionsGranted()) {
            sharedViewModel.onCameraPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                sharedViewModel.onCameraPermissionGranted()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        resetState()
    }

    private fun resetState() {
        viewModel.resetState()
        analyzer?.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        requestCameraPermissions()
        viewModel.recording.observe(viewLifecycleOwner, Observer {
            if (it) onStartRecording()
            else resetCamera()
        })

        return view
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
