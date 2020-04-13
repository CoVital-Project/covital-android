package org.covital.common.data.datasource.remote

import org.covital.common.domain.Either


class ItemsGateway(
    private val apiService: ApiService
) : BaseGateway() {

    suspend fun sendItems(requestBody: String): Either<Boolean> {
        return publicRequest { apiService.sendItems(requestBody) }
            .map { it.success }
    }

}
