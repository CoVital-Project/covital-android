package org.covital.common.data.datasource.remote

import org.covital.common.data.datasource.ItemsDataSource

class ItemsRemoteDataSource(
    private val apiService: ApiService
) : ItemsDataSource {

    override suspend fun sendItems(requestBody: String): Boolean {
        val response = apiService.sendItems(requestBody)
        return response.body()?.success ?: false
    }

}
