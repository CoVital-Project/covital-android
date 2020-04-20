package org.covital.main.usecases

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import org.covital.BuildConfig
import org.covital.common.data.repository.PermissionRepository
import org.covital.common.extensions.arePermissionsGranted
import org.covital.common.extensions.routeToActivity
import org.covital.common.extensions.showRationale
import org.covital.common.permissions.Permission

class MainInteractor(
    private val permissionRepository: PermissionRepository
) {

    companion object {
        const val REQUEST_CODE = 1000
    }

    fun requestPermission(activity: Activity, permission: Permission): Boolean {

        val manifest = permission.toManifest()

        when {
            activity.arePermissionsGranted(*manifest) -> {
                permissionRepository.setGranted(permission)
                return true
            }

            canRequestRuntimePermission(activity, permission) ->
                ActivityCompat.requestPermissions(activity, manifest, REQUEST_CODE)

            else -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                activity.routeToActivity(intent, requestCode = REQUEST_CODE)
            }
        }

        return false
    }

    private fun canRequestRuntimePermission(activity: Activity, permission: Permission): Boolean {
        val deniedForever = when (permission) {
            Permission.Camera -> permissionRepository.isDenied(Permission.Camera)
            else -> TODO("Not implemented")
        }

        val deniedOnce = when (permission) {
            Permission.Camera -> activity.showRationale(android.Manifest.permission.CAMERA)
            else -> TODO("Not implemented")
        }

        return when {
            deniedOnce -> true
            else -> !deniedForever
        }
    }

    fun onPermissionResult(requestCode: Int, permission: Permission, grantResults: IntArray): Boolean {
        if (requestCode != REQUEST_CODE) return false
        val granted = grantResults.isNotEmpty() && grantResults.first() == 0

        if (granted) {
            permissionRepository.setGranted(permission)
        } else {
            permissionRepository.setDenied(permission)
        }

        return granted
    }
}
