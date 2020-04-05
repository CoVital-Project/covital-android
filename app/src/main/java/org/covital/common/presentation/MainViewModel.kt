package org.covital.common.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.covital.common.presentation.navigation.Route

class MainViewModel(
    private val navigator: Navigator
) : ViewModel() {

    fun getLiveRouting(): LiveData<Route> {
        return navigator.directions
    }
}
