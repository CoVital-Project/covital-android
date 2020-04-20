package org.covital.account.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import org.covital.R
import org.covital.account.domain.DiagnosisItem
import org.covital.common.presentation.ui.BindableAdapter
import org.covital.databinding.DiagnosisItemBinding

open class DiagnosisAdapter(
    viewLifecycleOwner: LifecycleOwner,
    open val clicks: ((DiagnosisItem) -> Unit)?
) : BindableAdapter<DiagnosisItem>(viewLifecycleOwner) {

    init {
        setHasStableIds(true)
    }

    override fun getLayoutRes(position: Int): Int {
        return R.layout.diagnosis_item
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding {
        return when (viewType) {
            R.layout.diagnosis_item -> inflater.inflateBinding<DiagnosisItemBinding>(parent, viewType)
            else -> throw NotImplementedError("Binding not supported for viewType $viewType")
        }
    }

    override fun bind(binding: ViewDataBinding, item: DiagnosisItem, position: Int) {
        if (binding !is DiagnosisItemBinding) return

        binding.title = item.title
        binding.checked = item.checked
        binding.value.setOnCheckedChangeListener { _, isChecked ->
            clicks?.invoke(item.copy(checked = isChecked))
        }
    }
}
