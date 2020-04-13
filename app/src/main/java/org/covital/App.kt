package org.covital

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.zone.ZoneRulesProvider

class App : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        initKoin()

        AndroidThreeTen.init(this)

        // TODO: Should be performed on a background thread
        ZoneRulesProvider.getAvailableZoneIds()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}
