package org.covital.common.domain

object Try {

    inline operator fun <A> invoke(f: () -> A): Either<A> =
        try {
            Either.Success(f())
        } catch (e: Throwable) {
            Either.Failure(e)
        }
}
