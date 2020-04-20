package org.covital.common.data.repository

import org.covital.common.data.datasource.Prefs
import org.covital.common.permissions.Permission

class PermissionRepository(
    private val prefs: Prefs
) {

    fun getManifestPermissions(permission: Permission): Array<String> {
        return when (permission) {
            Permission.Camera -> arrayOf(android.Manifest.permission.CAMERA)
            else -> emptyArray()
        }
    }


    fun isDenied(permission: Permission): Boolean {
        return when (permission) {
            Permission.Camera -> prefs.cameraPermissionDenied
            else -> TODO("Not implemented")
        }
    }

    fun setDenied(permission: Permission) {
        when (permission) {
            Permission.Camera -> prefs.cameraPermissionDenied = true
        }
    }

    fun setGranted(permission: Permission) {
        when (permission) {
            Permission.Camera -> prefs.cameraPermissionDenied = false
        }
    }
}
