package org.covital.common.data.datasource.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.covital.common.domain.entities.Diagnosis

@Dao
abstract class DiagnosisDao : BaseDao<Diagnosis>() {

    @Query("SELECT COUNT(1) FROM diagnosis")
    abstract suspend fun getCount(): Int

    @Query("SELECT * FROM diagnosis WHERE persisted = 0")
    abstract suspend fun getPending(): List<Diagnosis>

    @Query("SELECT * FROM diagnosis")
    abstract fun getUpdates(): Flow<List<Diagnosis>>

    @Query("SELECT * FROM diagnosis")
    abstract suspend fun getAll(): List<Diagnosis>

}
