package org.covital.measurements.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentMeasurementResultBinding
import org.covital.sharedGraphViewModel

class MeasurementResultFragment : BaseFragment<FragmentMeasurementResultBinding>() {

    override val layoutRes: Int = R.layout.fragment_measurement_result

    private val sharedViewModel: MeasurementsViewModel by sharedGraphViewModel(R.id.measure_graph)

    override fun setupBinding(binding: FragmentMeasurementResultBinding) {

        binding.measurement = resources.getString(
            R.string.measurement_result_screen_value,
            sharedViewModel.measuredValue
        )
        binding.viewModel = sharedViewModel

    }
}
