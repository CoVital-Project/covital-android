package org.covital.measurements.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.covital.R
import org.covital.databinding.FragmentMeasurementResultBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MeasurementResultFragment : Fragment() {
    private val sharedViewModel: MeasurementsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMeasurementResultBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_measurement_result, container, false
        )
        binding.lifecycleOwner = this
        binding.measurement = resources.getString(
            R.string.measurement_result_screen_value,
            sharedViewModel.measuredValue
        )
        binding.viewModel = sharedViewModel
        return binding.root
    }
}