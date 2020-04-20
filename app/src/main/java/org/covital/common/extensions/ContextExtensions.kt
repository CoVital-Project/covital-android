package org.covital.common.extensions

import android.content.Context

fun Int.resName(context: Context?): String {
    return if (this == -1) {
        "moving"
    } else try {
        context?.resources?.getResourceEntryName(this) ?: "unknown"
    } catch (ex: Throwable) {
        "unknown"
    }
}
