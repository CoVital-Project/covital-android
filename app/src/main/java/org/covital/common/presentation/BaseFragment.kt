package org.covital.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<Binding: ViewDataBinding> : Fragment() {

    abstract val layoutRes: Int

    lateinit var ui: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = DataBindingUtil.inflate<Binding>(inflater, layoutRes, container, false)
        ui.lifecycleOwner = viewLifecycleOwner
        setupBinding(ui)
        return ui.root
    }

    open fun setupBinding(binding: Binding) {}

    open fun isFinishing(): Boolean {
        return activity?.isFinishing ?: true
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    protected fun setStatusBarDarkness(opacity: Float, progress: Float = 1f) {
        (requireActivity() as? MainActivity)?.setStatusBarDarkness(opacity, progress)
    }
}
