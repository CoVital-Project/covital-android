package org.covital.common.permissions

enum class Permission {
    Camera;

    fun toManifest(): Array<String> {
        return when (this) {
            Camera -> arrayOf(android.Manifest.permission.CAMERA)
            else -> emptyArray()
        }
    }

    companion object {

        @JvmStatic fun fromManifest(values: Array<out String>): Permission? {
            return when {
                values.contains(android.Manifest.permission.CAMERA) -> Camera
                else -> null
            }
        }
    }
}
