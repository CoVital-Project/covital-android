package org.covital.common.data.repository

import kotlinx.coroutines.flow.Flow
import org.covital.common.data.utils.DateAdapter
import org.covital.common.domain.Either
import org.covital.common.domain.Try
import org.covital.common.domain.entities.User
import org.covital.common.domain.model.HeightSystem
import org.covital.common.domain.model.SkinTone
import org.covital.common.domain.model.WeightSystem
import org.threeten.bp.Instant

class UserRespository(
    val database: Database
) {

    suspend fun persist(user: User): Long {
        return database.user().upsert(user)
    }

    suspend fun getCount(): Int {
        return database.user().getCount()
    }

    suspend fun getPending(): List<User> {
        return database.user().getPending()
    }

    suspend fun getUpdates(): Flow<List<User>> {
        return database.user().getUpdates()
    }

    suspend fun upsert(user: User) {
        database.user().upsert(user)
//        database.diagnosis().upsertMany(*userView.diagnoses.toTypedArray())

        // TODO: Send user record changes to API

        // TODO: Persist that user record changes have been persisted
    }

    suspend fun getUser(): User {
        return database.user().getUser()
    }
}
