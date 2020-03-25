package org.covital.measurements.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.covital.R
import org.covital.databinding.ActivityMeasurementsBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeasurementsActivity : AppCompatActivity() {

    private val measurementsViewModel: MeasurementsViewModel by currentScope.viewModel(this)

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
    }

}
