package org.covital.common.data.datasource.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.covital.common.domain.entities.Recording

@Dao
abstract class RecordingDao : BaseDao<Recording>() {

    @Query("SELECT COUNT(1) FROM recording")
    abstract suspend fun getCount(): Int

    @Query("SELECT * FROM recording WHERE remoteId ISNULL")
    abstract suspend fun getPending(): List<Recording>

    @Query("SELECT * FROM recording")
    abstract fun getUpdates(): Flow<List<Recording>>

}
