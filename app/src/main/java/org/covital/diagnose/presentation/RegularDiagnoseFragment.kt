package org.covital.diagnose.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentDiagnoseBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class RegularDiagnoseFragment : BaseFragment<FragmentDiagnoseBinding>() {

    override val layoutRes: Int = R.layout.fragment_diagnose

    private val viewModel: RegularDiagnoseViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentDiagnoseBinding) {
        binding.viewModel = viewModel
    }
}
