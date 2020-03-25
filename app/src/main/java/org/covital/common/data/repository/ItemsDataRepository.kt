package org.covital.common.data.repository

import org.covital.common.data.datasource.ItemsDataSource
import org.covital.common.domain.ItemsRepository
import org.covital.common.domain.model.ResultWrapper
import retrofit2.HttpException
import java.io.IOException

class ItemsDataRepository(private val remoteDataSource: ItemsDataSource) : ItemsRepository {

    override suspend fun sendItems(requestBody: String): ResultWrapper<Boolean> {
        return try {
            ResultWrapper.Success(remoteDataSource.sendItems(requestBody))
        } catch (throwable: IOException) {
            ResultWrapper.NetworkError
        } catch (throwable: HttpException) {
            val code = throwable.code()
            val errorResponse = throwable.message()
            ResultWrapper.GenericError(code, errorResponse)
        }
    }
}
