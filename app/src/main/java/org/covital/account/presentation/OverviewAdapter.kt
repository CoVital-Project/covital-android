package org.covital.account.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import org.covital.R
import org.covital.account.domain.OverviewItem
import org.covital.common.extensions.formatYMD
import org.covital.common.presentation.ui.BindableAdapter
import org.covital.databinding.OverviewItemBinding

open class OverviewAdapter(
    viewLifecycleOwner: LifecycleOwner,
    open val clicks: ((OverviewItem) -> Unit)?
) : BindableAdapter<OverviewItem>(viewLifecycleOwner) {

    init {
        setHasStableIds(true)
    }

    override fun getLayoutRes(position: Int): Int {
        return R.layout.overview_item
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding {
        return inflater.inflateBinding<OverviewItemBinding>(parent, viewType)
    }

    override fun bind(binding: ViewDataBinding, item: OverviewItem, position: Int) {

        if (binding !is OverviewItemBinding) return
        val context = binding.root.context


        when (item) {
            is OverviewItem.DateOfBirth -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_date_of_birth)
                binding.itemTitle = "Date of Birth"
                binding.itemValue = when (item.date) {
                    null -> "Unknown"
                    else -> item.date.formatYMD()
                }
                binding.valueVisible = true
            }
            is OverviewItem.Gender -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_gender)
                binding.itemTitle = "Gender"
                binding.itemValue = when (item.value) {
                    null -> "Unknown"
                    else -> item.value.name
                }
                binding.valueVisible = true
            }
            is OverviewItem.Weight -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_weight)
                binding.itemTitle = "Weight"
                binding.itemValue = when (item.value) {
                    null -> "Unknown"
                    else -> item.system.applyFormat(item.value)
                }
                binding.valueVisible = true
            }
            is OverviewItem.Height -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_height)
                binding.itemTitle = "Height"
                binding.itemValue = when (item.value) {
                    null -> "Unknown"
                    else -> item.system.applyFormat(item.value)
                }
                binding.valueVisible = true
            }
            is OverviewItem.CovidTest -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_test_results)
                binding.itemTitle = "Covid Test"
                binding.itemValue = when (item.result) {
                    true -> "Yes"
                    false -> "No"
                    else -> "Unknown"
                }
                binding.valueVisible = true
            }
            is OverviewItem.Skin -> {
                binding.itemIcon = ContextCompat.getDrawable(context, R.drawable.ic_skin_tone)
                binding.itemTitle = "Skin Tone"

                when (val skinTone = item.skinTone?.colorRes) {
                    null -> {
                        binding.valueVisible = true
                        binding.itemValue = "Unknown"
                        binding.skinToneVisible = false
                    }
                    else -> {
                        binding.valueVisible = false
                        binding.skinToneVisible = true
                        binding.skinToneColor = ContextCompat.getColor(context, skinTone)
                    }
                }
            }
        }

        binding.surface.setOnClickListener {
            clicks?.invoke(item)
        }
    }
}
