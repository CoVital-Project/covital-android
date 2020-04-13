package org.covital

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.jakewharton.threetenabp.AndroidThreeTen
import org.covital.common.data.datasource.Prefs
import org.covital.common.data.datasource.PrefsFactory
import org.covital.common.logging.Acorn
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.threeten.bp.Instant
import org.threeten.bp.zone.ZoneRulesProvider
import timber.log.Timber

class App : Application(), CameraXConfig.Provider {

    private val prefs: Prefs by inject { parametersOf(this) }
    private val networkPlugin: NetworkFlipperPlugin by inject { parametersOf(this) }
    private val acorn: Acorn by inject { parametersOf(this) }

    override fun onCreate() {
        super.onCreate()
        initKoin()

        Timber.plant(acorn)

        AndroidThreeTen.init(this)

        val currentVersion = BuildConfig.VERSION_CODE
        val lastVersion = prefs.lastVersionCode ?: 0

        // If not upgrading to a newer version
        if (lastVersion < currentVersion) {
            prefs.lastVersionCode = currentVersion
            prefs.lastAppUpgrade = Instant.now()
            onUpgrade()
        }

        if (prefs.firstAppLaunch == null) {
            prefs.firstAppLaunch = Instant.now()
        }
        prefs.lastAppLaunch = Instant.now()

        // TODO: Should be performed on a background thread
        ZoneRulesProvider.getAvailableZoneIds()

        startFirebaseIfServicesExist()

        // TODO: This should only be run on internal debug builds
        SoLoader.init(this, false)

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(SharedPreferencesFlipperPlugin(this, PrefsFactory.NAME))
            client.addPlugin(networkPlugin)
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(LeakCanaryFlipperPlugin())
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    private fun startFirebaseIfServicesExist() {
        try {
            if (GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS
            ) {

                FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true

                if (BuildConfig.DEBUG) {
                    FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
                }
            }
        } catch (ex: Throwable) {

        }
    }

    private fun onUpgrade() {

    }
}
