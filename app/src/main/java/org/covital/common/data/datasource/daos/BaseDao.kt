package org.covital.common.data.datasource.daos

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(data: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertMany(vararg data: T)

}
