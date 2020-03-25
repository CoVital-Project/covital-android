package org.covital.common.domain

import org.covital.common.domain.model.ResultWrapper

/**
 * Dummy repository, name it as you need it.
 */
interface ItemsRepository {

    suspend fun sendItems(requestBody: String): ResultWrapper<Boolean>
}
