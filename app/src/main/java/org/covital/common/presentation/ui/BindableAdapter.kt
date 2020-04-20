package org.covital.common.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil

abstract class BindableAdapter<T : Comparable<T>>(
    protected val viewLifecycleOwner: LifecycleOwner
) : AsyncListAdapter<T>(GenericDiffCallback<T>()) {

    override fun getItemViewType(position: Int): Int {
        return getLayoutRes(position)
    }

    @LayoutRes
    abstract fun getLayoutRes(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        return BindableViewHolder(createBinding(LayoutInflater.from(parent.context), parent, viewType))
    }

    protected abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding

    protected fun <Binding : ViewDataBinding> LayoutInflater.inflateBinding(
        parent: ViewGroup,
        viewType: Int
    ): Binding {

        return DataBindingUtil.inflate(
            this,
            viewType,
            parent,
            false
        )
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        if (position < itemCount) {
            bind(holder.binding, getItem(position), position)
            holder.binding.executePendingBindings()
        }
    }

    protected abstract fun bind(binding: ViewDataBinding, item: T, position: Int)

    override fun onViewAttachedToWindow(holder: BindableViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: BindableViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }
}
