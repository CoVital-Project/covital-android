package org.covital.dashboard.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentDashboardBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override val layoutRes: Int = R.layout.fragment_dashboard

    private val viewModel: DashboardViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentDashboardBinding) {
        binding.viewModel = viewModel
    }
}
