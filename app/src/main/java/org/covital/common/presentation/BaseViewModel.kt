package org.covital.common.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _state = MutableLiveData<UIModel>()
    val data: LiveData<UIModel>
        get() = _state

}
