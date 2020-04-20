package org.covital.common.presentation.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class AsyncListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, BindableViewHolder>(AsyncDifferConfig.Builder<T>(diffCallback).build())
