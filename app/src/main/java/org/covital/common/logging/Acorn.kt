package org.covital.common.logging

import android.util.Log
import timber.log.Timber

class Acorn(private val logcatEnabled: Boolean) : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        val crashlytics = FirebaseCrashlytics.getInstance()
        if (priority == Log.ERROR) {
//            crashlytics.log("$priority|$tag: $message")
        }
        if (t != null) {
//            crashlytics.recordException(t)
        }
        if (logcatEnabled) {
            super.log(priority, tag, message, t)
        }
    }
}
