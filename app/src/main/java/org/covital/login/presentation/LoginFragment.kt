package org.covital.login.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentLoginBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutRes: Int = R.layout.fragment_login

    private val viewModel: LoginViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentLoginBinding) {
        binding.viewModel = viewModel
    }
}
