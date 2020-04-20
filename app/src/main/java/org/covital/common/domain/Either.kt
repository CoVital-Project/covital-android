package org.covital.common.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.IllegalStateException

typealias Success<T> = Either.Success<T>
typealias Failure<T> = Either.Failure<T>

sealed class Either<T> {
    data class Success<T>(val value: T) : Either<T>()
    data class Failure<T>(val error: Throwable): Either<T>()

    fun <R> map(mapper: (T) -> R): Either<R> {
        return when (this) {
            is Success -> {
                try {
                    Success(mapper(value))
                } catch (ex: Throwable) {
                    Failure<R>(ex)
                }
            }
            is Failure -> Failure<R>(
                error
            )
        }
    }

    suspend fun <R> pmap(mapper: suspend (T) -> R): Either<R> {
        return when (this) {
            is Success -> {
                try {
                    Success(mapper(value))
                } catch (ex: Throwable) {
                    Failure<R>(ex)
                }
            }
            is Failure -> Failure<R>(
                error
            )
        }
    }

    fun mapError(mapper: (Throwable) -> Either<T>): Either<T> {
        return when (this) {
            is Success -> Success(
                value
            )
            is Failure -> mapper(error)
        }
    }

    fun get(): T? {
        return when (this) {
            is Success -> value
            is Failure -> null
        }
    }

    fun error(): Throwable? {
        return when (this) {
            is Success -> null
            is Failure -> error
        }
    }

    fun or(block: () -> T): T {
        return get() ?: block()
    }

    companion object {
        @JvmStatic fun <T> of(block: () -> T): Either<T> {
            return try {
                Success(block())
            } catch (ex: Throwable) {
                Failure(ex)
            }
        }

        @JvmStatic fun <T> just(value: T): Either<T> {
            return Success(value)
        }

        @JvmStatic fun error(ex: Throwable): Either<Any> {
            return Failure(ex)
        }
    }
}

fun <T, R> Either<List<T>>.remap(mapper: (T) -> R): Either<List<R>> {
    return map { it.map(mapper) }
}
