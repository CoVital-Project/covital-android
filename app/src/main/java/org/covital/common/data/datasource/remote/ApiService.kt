package org.covital.common.data.datasource.remote

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    /**
     * Dummy endpoint, update with real one
     */
    @POST("search/repositories")
    suspend fun sendItems(
        @Body body: String
    ): Response<DummyResponse>
}

/**
 * Update this response with real backend one.
 */
class DummyResponse(
    @Json(name = "status") val status: Int,
    @Json(name = "success") val success: Boolean
)
