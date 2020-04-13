package org.covital

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.zone.ZoneRulesProvider

class App : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        initKoin()

        AndroidThreeTen.init(this)

        // TODO: Should be performed on a background thread
        ZoneRulesProvider.getAvailableZoneIds()

        startFirebaseIfServicesExist()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    private fun startFirebaseIfServicesExist() {
        try {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

                FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true

                if (BuildConfig.DEBUG) {
                    FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
                }
            }
        } catch (ex: Throwable) {

        }
    }
}
