package org.covital.common.domain

import org.junit.Assert.*
import org.junit.Test
import java.lang.NullPointerException

class EitherTest {

    @Test
    fun `create a wrapped success`() {
        val result = Either.just("Hoorah")

        assert(result is Success)
        result.get().let { value ->
            assertNotNull(value)
            assertEquals(value, "Hoorah")
        }
        assertNull(result.error())
    }

    @Test
    fun `create a wrapped error`() {
        val result = Either.error(NullPointerException("oops"))

        assert(result is Failure)
        result.error().let { error ->
            assertNotNull(error)
            assert(error is NullPointerException)
            assertEquals(error?.message, "oops")
        }
        assertNull(result.get())
    }
}
