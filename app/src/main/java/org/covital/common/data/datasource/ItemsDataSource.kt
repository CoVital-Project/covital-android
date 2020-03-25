package org.covital.common.data.datasource


/**
 * Dummy datasource, name it as you need it.
 */
interface ItemsDataSource {

    suspend fun sendItems(requestBody: String): Boolean
}
