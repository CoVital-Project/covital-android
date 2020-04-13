package org.covital.settings.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentSettingsBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override val layoutRes: Int = R.layout.fragment_settings

    private val viewModel: SettingsViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentSettingsBinding) {
        binding.viewModel = viewModel
    }
}
