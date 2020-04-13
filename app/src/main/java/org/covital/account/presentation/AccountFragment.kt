package org.covital.account.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentAccountBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    override val layoutRes: Int = R.layout.fragment_account

    private val viewModel: AccountViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentAccountBinding) {
        binding.viewModel = viewModel
    }
}
