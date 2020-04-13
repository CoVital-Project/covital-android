package org.covital.common.data.datasource.remote

import android.content.Context
import okhttp3.*
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.covital.common.data.datasource.MoshiFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceFactory {

    // Update this url with the real backend
    private const val BASE_URL = "https://api.github.com"

    const val OKHTTP_CACHE_SIZE = 10L * 1024 * 1024

    fun create(context: Context, moshi: Moshi): ApiService {

        val okhttp: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, OKHTTP_CACHE_SIZE))
                .addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(object: Call.Factory {
                override fun newCall(request: Request): Call {
                    return okhttp.newCall(request)
                }
            })
            .addConverterFactory(EmptyResponseFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}
