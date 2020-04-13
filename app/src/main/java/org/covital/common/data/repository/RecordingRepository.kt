package org.covital.common.data.repository

import kotlinx.coroutines.flow.Flow
import org.covital.common.domain.entities.Recording

class RecordingRepository(
    val database: Database
) {

    suspend fun persist(recording: Recording): Long {
        return database.recording().upsert(recording)
    }

    suspend fun getCount(): Int {
        return database.recording().getCount()
    }

    suspend fun getPending(): List<Recording> {
        return database.recording().getPending()
    }

    suspend fun getUpdates(recording: Recording): Flow<List<Recording>> {
        return database.recording().getUpdates()
    }
}
