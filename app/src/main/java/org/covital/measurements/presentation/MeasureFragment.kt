package org.covital.measurements.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.covital.R
import org.covital.databinding.FragmentMeasureBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class MeasureFragment : Fragment() {
    private val sharedViewModel: MeasurementsViewModel by sharedViewModel()
    private val viewModel: MeasureViewModel by lifecycleScope.viewModel(this) {
        parametersOf(sharedViewModel)
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
        viewModel.recording.observe(viewLifecycleOwner, Observer { if (it) onStartRecording() })
        return binding.root
    }

    private fun onStartRecording() {
        // TODO record video
    }
}