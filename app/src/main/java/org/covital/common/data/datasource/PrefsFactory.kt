package org.covital.common.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext

object PrefsFactory {

    const val NAME = "prefs"

    /**
     * See the following link for current recommended setup:
     *   https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
     */
    fun create(context: Context, moshi: Moshi): Prefs {
        return Prefs(EncryptedSharedPreferences.create(
            NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ), moshi)
    }
}
