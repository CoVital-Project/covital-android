package org.covital.explain.presentation

import org.covital.R
import org.covital.common.extensions.*
import org.covital.common.permissions.Permission
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentExplainBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class ExplainFragment : BaseFragment<FragmentExplainBinding>() {

    override val layoutRes: Int = R.layout.fragment_explain

    private val viewModel: ExplainViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentExplainBinding) {
        binding.viewModel = viewModel

        observe(viewModel.approver.results) { (permission, granted) ->
            when {
                permission == Permission.Camera && granted -> viewModel.onCameraPermissionAccepted()
            }
        }

        observe(viewModel.transitions) { (from, to) ->
            binding.rootMotionLayout.apply {
                if (currentState != to) {
                    transitionTo(from, to)
                }
            }
        }

        binding.rootMotionLayout.after {
            val from = binding.rootMotionLayout.startState
            val to = binding.rootMotionLayout.currentState
            viewModel.onTransitionCompleted(from, to)
        }
    }

    override fun onBackPressed(): Boolean {
        return viewModel.onBackTapped()
    }
}
