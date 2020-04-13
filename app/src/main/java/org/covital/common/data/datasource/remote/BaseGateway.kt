package org.covital.common.data.datasource.remote

import org.covital.common.domain.Either
import org.covital.common.domain.Try
import retrofit2.Response

abstract class BaseGateway {

    open suspend fun <T> publicRequest(request: suspend () -> Response<T>): Either<T> {
        return Try { request().body()!! }
    }
}
