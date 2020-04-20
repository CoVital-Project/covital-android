package org.covital.common.navigation

import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import org.covital.common.presentation.utils.SingleLiveEvent

open class Navigator {

    private val _directions = SingleLiveEvent<Route>()
    val directions: LiveData<Route> = _directions

    open fun goTo(directions: NavDirections) {
        _directions.postValue(Route.Forward(directions))
    }

    open fun back() {
        _directions.postValue(Route.Back)
    }

}
