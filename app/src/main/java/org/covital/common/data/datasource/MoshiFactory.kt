package org.covital.common.data.datasource

import com.squareup.moshi.Moshi
import org.covital.common.data.utils.DateAdapter

object MoshiFactory {
    fun create(): Moshi {
        return Moshi.Builder().add(DateAdapter()).build()
    }
}
