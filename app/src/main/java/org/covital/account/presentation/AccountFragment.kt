package org.covital.account.presentation

import android.graphics.Color
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import org.covital.R
import org.covital.account.domain.RecordingItem
import org.covital.common.domain.Failure
import org.covital.common.domain.Success
import org.covital.common.extensions.observe
import org.covital.common.presentation.BaseFragment
import org.covital.common.presentation.ui.StatusBar
import org.covital.databinding.FragmentAccountBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    override val layoutRes: Int = R.layout.fragment_account

    private val viewModel: AccountViewModel by lifecycleScope.viewModel(this)

    override fun setupBinding(binding: FragmentAccountBinding) {
        binding.viewModel = viewModel

        binding.overviewOptions.adapter = OverviewAdapter(
            viewLifecycleOwner = viewLifecycleOwner
        ) {
            viewModel.onOverviewItemTapped(it)
        }

        observe(viewModel.userAndOverview) { result ->
            val viewState = result.get() ?: return@observe
            binding.email.text = viewState.first.email
            binding.getOverviewAdapter()?.submitList(viewState.second)
        }

        binding.medicalDiagnoses.adapter = DiagnosisAdapter(
            viewLifecycleOwner = viewLifecycleOwner
        ) {
            viewModel.onDiagnosisItemTapped(it)
        }

        observe(viewModel.diagnoses) { result ->
            binding.getDiagnosisAdapter()?.submitList(result)
        }

        binding.historyList.adapter = RecordingAdapter(
            viewLifecycleOwner = viewLifecycleOwner
        ) {
            viewModel.onHistoryItemTapped(it)
        }

        observe(viewModel.history) { result ->
            val update = when (result) {
                is Success -> result.value
                is Failure -> listOf(RecordingItem.Error(result.error))
            }

            binding.getRecordingAdapter()?.submitList(update)
        }

        binding.historyButton.setOnClickListener {
            val lastProgress = binding.rootMotionLayout.progress
            if (lastProgress > 0f) {
                binding.rootMotionLayout.progress = lastProgress
                binding.rootMotionLayout.transitionToStart()
            } else {
                binding.rootMotionLayout.transitionToEnd()
            }
        }

        binding.rootMotionLayout.setTransitionListener(object: MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
                setStatusBarDarkness(0.53f, progress)
            }
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}
        })
    }

    private fun FragmentAccountBinding.getOverviewAdapter(): OverviewAdapter? {
        return overviewOptions.adapter as? OverviewAdapter
    }

    private fun FragmentAccountBinding.getDiagnosisAdapter(): DiagnosisAdapter? {
        return medicalDiagnoses.adapter as? DiagnosisAdapter
    }

    private fun FragmentAccountBinding.getRecordingAdapter(): RecordingAdapter? {
        return historyList.adapter as? RecordingAdapter
    }
}
