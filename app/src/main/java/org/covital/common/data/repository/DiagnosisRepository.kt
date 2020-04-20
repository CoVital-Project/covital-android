package org.covital.common.data.repository

import kotlinx.coroutines.flow.Flow
import org.covital.common.domain.Either
import org.covital.common.domain.Try
import org.covital.common.domain.entities.Diagnosis

class DiagnosisRepository(
    private val database: Database
) {

    suspend fun persist(diagnosis: Diagnosis): Long {
        return database.diagnosis().upsert(diagnosis)
    }

    suspend fun getCount(): Int {
        return database.diagnosis().getCount()
    }

    suspend fun getPending(): List<Diagnosis> {
        return database.diagnosis().getPending()
    }

    suspend fun getUpdates(diagnosis: Diagnosis): Flow<List<Diagnosis>> {
        return database.diagnosis().getUpdates()
    }

    suspend fun getAll(): List<Diagnosis> {
        return database.diagnosis().getAll()
    }
}
