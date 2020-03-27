package org.covital.measurements.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.covital.R
import org.covital.databinding.ActivityMeasurementsBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.viewModel

class MeasurementsActivity : AppCompatActivity() {

    private val measurementsViewModel: MeasurementsViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
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
