package org.covital.onboarding.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentOnboardingBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override val layoutRes: Int = R.layout.fragment_onboarding

    private val viewModel: OnboardingViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentOnboardingBinding) {
        binding.viewModel = viewModel
    }
}
