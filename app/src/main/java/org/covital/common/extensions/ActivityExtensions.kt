package org.covital.common.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.covital.R


fun Activity.isCameraGranted(): Boolean {
    return arePermissionsGranted(android.Manifest.permission.CAMERA)
}

fun Activity.arePermissionsGranted(vararg permission: String): Boolean {
    return permission.map { ActivityCompat.checkSelfPermission(this, it) }
        .all { it == PackageManager.PERMISSION_GRANTED }
}

fun Activity.showRationale(vararg permission: String): Boolean {
    return permission.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }
}

fun Activity.routeToActivity(
    intent: Intent,
    clearStack: Boolean = false,
    clearTop: Boolean = false,
    useChooser: Boolean = false,
    @StringRes chooserTitle: Int? = null,
    onFailure: String? = null,
    requestCode: Int? = null,
    options: Bundle? = null): Boolean {

    when {
        useChooser -> return routeToActivity(
            this,
            intent,
            chooser = true,
            chooserTitle = chooserTitle,
            onFailure = onFailure,
            requestCode = requestCode,
            options = options)
        clearStack -> intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        clearTop -> intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    val result = routeToActivity(
        this,
        intent,
        chooser = false,
        chooserTitle = chooserTitle,
        onFailure = onFailure,
        requestCode = requestCode,
        options = options)
    return result
}

fun Activity.canLoadResources(@DrawableRes resourceId: Int): Boolean {

    val result = try {
        ContextCompat.getDrawable(baseContext, resourceId)
    } catch (ex: Exception) {
        null
    }

    return result != null
}

private fun routeToActivity(
    activity: Activity,
    intent: Intent,
    chooser: Boolean,
    @StringRes chooserTitle: Int?,
    onFailure: String? = null,
    requestCode: Int? = null,
    options: Bundle? = null): Boolean {

    if (!hasHandler(activity, intent)) {
        when (onFailure == null || onFailure.isNullOrBlank()) {
            true -> Toast.makeText(activity, activity.getString(R.string.no_intent_handler, intent.action), Toast.LENGTH_LONG)
            false -> Toast.makeText(activity, onFailure, Toast.LENGTH_LONG)
        }?.show()
        return false
    }

    if (requestCode != null) {
        if (options != null) {
            activity.startActivityForResult(intent, requestCode, options)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    } else {
        val finalIntent = when {
            chooser -> {
                val title = if (chooserTitle == null) {
                    "Share with Friends"
                } else {
                    activity.getString(chooserTitle)
                }
                Intent.createChooser(intent, title)
            }
            else -> intent
        }
        if (options != null) {
            try {
                activity.startActivity(finalIntent, options)
            } catch (ex: NullPointerException) {
                return false
            }
        } else {
            activity.startActivity(finalIntent)
        }
    }

    return true
}

/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
private fun hasHandler(activity: Activity, intent: Intent): Boolean {
    val handlers = activity.packageManager?.queryIntentActivities(intent, 0)
    return !(handlers?.isEmpty() ?: true)
}

tailrec fun getActivity(context: Context?): Activity? = when (context) {
    is Activity -> context
    is ContextWrapper -> getActivity(context.baseContext)
    else -> null
}
