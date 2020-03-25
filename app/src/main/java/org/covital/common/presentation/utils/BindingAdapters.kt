package org.covital.common.presentation.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.covital.common.presentation.UIModel

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}

@BindingAdapter("loading")
fun View.setLoadingVisibility(uiModel: UIModel?) {
    uiModel?.let {
        visibility = if (it is UIModel.Loading) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("onError")
fun View.setOnErrorVisibility(uiModel: UIModel?) {
    uiModel?.let {
        visibility = if (it is UIModel.Error) View.VISIBLE else View.GONE
    }
}
