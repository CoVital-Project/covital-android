package org.covital.account.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import org.covital.R
import org.covital.account.domain.RecordingItem
import org.covital.common.extensions.getFriendlyDayName
import org.covital.common.extensions.getHourMinuteAmPm
import org.covital.common.presentation.ui.BindableAdapter
import org.covital.databinding.RecordingNewItemBinding
import org.covital.databinding.RecordingItemBinding
import org.threeten.bp.Instant

open class RecordingAdapter(
    viewLifecycleOwner: LifecycleOwner,
    open val clicks: ((RecordingItem) -> Unit)?
) : BindableAdapter<RecordingItem>(viewLifecycleOwner) {

    override fun getLayoutRes(position: Int): Int {
        return when (position) {
            0 -> R.layout.recording_new_item
            else -> R.layout.recording_item
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding {
        return when (viewType) {
            R.layout.recording_new_item -> inflater.inflateBinding<RecordingNewItemBinding>(parent, viewType)
            R.layout.recording_item -> inflater.inflateBinding<RecordingItemBinding>(parent, viewType)
            else -> throw NotImplementedError("Binding not supported for viewType $viewType")
        }
    }

    override fun bind(binding: ViewDataBinding, item: RecordingItem, position: Int) {

        if (binding is RecordingItemBinding && item is RecordingItem.Recording) {
            val now = Instant.now()
            binding.hgo2 = item.hgo2
            binding.bpm = item.bpm
            binding.day = now.getFriendlyDayName()
            binding.time = now.getHourMinuteAmPm()
            binding.backgroundSurface.setOnClickListener {
                clicks?.invoke(item)
            }
        } else if (binding is RecordingNewItemBinding) {

            binding.addNewMeasurement.setOnClickListener {
                clicks?.invoke(item)
            }
        }
    }
}
