package org.covital.common.data.repository

import org.covital.common.data.datasource.remote.ItemsGateway
import org.covital.common.domain.Either

class ItemsRepository(private val gateway: ItemsGateway) {

    suspend fun sendItems(requestBody: String): Either<Boolean> {
        return gateway.sendItems(requestBody)
    }
}
