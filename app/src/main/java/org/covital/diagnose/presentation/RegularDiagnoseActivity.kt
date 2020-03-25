package org.covital.diagnose.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.covital.R
import org.covital.databinding.ActivityDiagnoseBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegularDiagnoseActivity : AppCompatActivity() {

    private val regularDiagnoseViewModel: RegularDiagnoseViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
    }

    private fun setupDataBinding() {
        val dataBinding: ActivityDiagnoseBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_diagnose)

        dataBinding.apply {
            lifecycleOwner = this@RegularDiagnoseActivity
            viewModel = regularDiagnoseViewModel
        }
    }

}
