package org.covital

import android.app.Application
import org.covital.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
