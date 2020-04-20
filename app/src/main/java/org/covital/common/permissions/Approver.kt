package org.covital.common.permissions

import androidx.lifecycle.LiveData
import org.covital.common.presentation.utils.SingleLiveEvent

open class Approver {

    private val _requests = SingleLiveEvent<Permission>()
    val requests: LiveData<Permission> = _requests
    private val _results = SingleLiveEvent<Pair<Permission, Boolean>>()
    val results: LiveData<Pair<Permission, Boolean>> = _results

    open fun request(requested: Permission) {
        _requests.postValue(requested)
    }

    open fun postResult(requested: Permission, granted: Boolean) {
        _results.postValue(requested to granted)
    }
}
