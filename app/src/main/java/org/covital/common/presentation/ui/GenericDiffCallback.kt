package org.covital.common.presentation.ui

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T : Comparable<T>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.compareTo(newItem) == 0
    }
}
