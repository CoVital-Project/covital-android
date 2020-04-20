package org.covital.main.presentation

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.covital.common.navigation.Navigator
import org.covital.common.navigation.Route
import org.covital.common.permissions.Approver
import org.covital.common.permissions.Permission
import org.covital.main.usecases.MainInteractor

class MainViewModel(
    private val navigator: Navigator,
    private val approver: Approver,
    private val interactor: MainInteractor
) : ViewModel() {

    fun getLiveRouting(): LiveData<Route> {
        return navigator.directions
    }

    fun getLivePermissions(): LiveData<Permission> {
        return approver.requests
    }

    fun requestPermission(activity: Activity, permission: Permission) {
        if (interactor.requestPermission(activity, permission)) {
            approver.postResult(permission, granted = true)
        }
    }

    fun onPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val permission = Permission.fromManifest(permissions) ?: return
        val result = interactor.onPermissionResult(requestCode, permission, grantResults)
        approver.postResult(permission, result)
    }
}
