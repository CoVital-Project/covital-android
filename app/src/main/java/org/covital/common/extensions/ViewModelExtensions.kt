package org.covital.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    viewLifecycleOwner.observeLifeCycle(liveData, observer)
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    observeLifeCycle(liveData, observer)
}

private fun <T> LifecycleOwner.observeLifeCycle(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer { observer(it) })
}