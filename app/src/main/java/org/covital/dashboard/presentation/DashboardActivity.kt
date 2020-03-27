package org.covital.dashboard.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.covital.R
import org.covital.databinding.ActivityDashboardBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class DashboardActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
    }

    private fun setupDataBinding() {
        val dataBinding: ActivityDashboardBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        dataBinding.apply {
            lifecycleOwner = this@DashboardActivity
            viewModel = dashboardViewModel
        }
    }

}
