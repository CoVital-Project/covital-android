package org.covital.measurements.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.covital.R
import org.covital.databinding.ActivityMeasurementsBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO
)
class MeasurementsActivity : AppCompatActivity() {

    private val measurementsViewModel: MeasurementsViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        requestCameraPermissions()
    }

    private fun requestCameraPermissions() {
        if (allPermissionsGranted()) {
            measurementsViewModel.onCameraPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                measurementsViewModel.onCameraPermissionGranted()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupDataBinding() {
        val dataBinding: ActivityMeasurementsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_measurements)

        dataBinding.apply {
            lifecycleOwner = this@MeasurementsActivity
            viewModel = measurementsViewModel
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MeasureFragment())
            .commit()

        measurementsViewModel.measureFinishedEvent.observe(this, Observer {
            navigateToResultScreen()
        })
        measurementsViewModel.resultFinishedEvent.observe(this, Observer {
            navigateToImproveMeasurementScreen()
        })
    }

    private fun navigateToResultScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MeasurementResultFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToImproveMeasurementScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ImproveMeasurementsFragment())
            .addToBackStack(null)
            .commit()
    }

}
