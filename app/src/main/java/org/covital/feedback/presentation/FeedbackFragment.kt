package org.covital.feedback.presentation

import org.covital.R
import org.covital.common.presentation.BaseFragment
import org.covital.databinding.FragmentFeedbackBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class FeedbackFragment : BaseFragment<FragmentFeedbackBinding>() {

    override val layoutRes: Int = R.layout.fragment_feedback

    private val viewModel: FeedbackViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentFeedbackBinding) {
        binding.viewModel = viewModel
    }
}
