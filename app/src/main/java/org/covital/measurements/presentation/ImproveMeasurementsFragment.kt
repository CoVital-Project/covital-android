package org.covital.measurements.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.covital.R
import org.covital.databinding.FragmentImproveMeasurementsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImproveMeasurementsFragment : Fragment() {
    private val sharedViewModel: MeasurementsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentImproveMeasurementsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_improve_measurements, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel
        return binding.root
    }
}