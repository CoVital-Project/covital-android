package org.covital.common.data.datasource.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.covital.common.domain.entities.User

@Dao
abstract class UserProfileDao : BaseDao<User>() {

    @Query("SELECT COUNT(1) FROM user")
    abstract suspend fun getCount(): Int

    @Query("SELECT * FROM user LIMIT 1")
    abstract suspend fun getUser(): User

    @Query("SELECT * FROM user WHERE persisted = 0")
    abstract suspend fun getPending(): List<User>

    @Query("SELECT * FROM user")
    abstract fun getUpdates(): Flow<List<User>>

}
