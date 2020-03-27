package org.covital.login.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.covital.R
import org.covital.databinding.ActivityLoginBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
    }

    private fun setupDataBinding() {
        val dataBinding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        dataBinding.apply {
            lifecycleOwner = this@LoginActivity
            viewModel = loginViewModel
        }
    }
}