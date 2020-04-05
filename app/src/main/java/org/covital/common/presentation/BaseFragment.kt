package org.covital.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.covital.databinding.FragmentImproveMeasurementsBinding

abstract class BaseFragment<Binding: ViewDataBinding> : Fragment() {

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<Binding>(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setupBinding(binding)
        return binding.root
    }

    open fun setupBinding(binding: Binding) {}

    open fun isFinishing(): Boolean {
        return activity?.isFinishing ?: true
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}
