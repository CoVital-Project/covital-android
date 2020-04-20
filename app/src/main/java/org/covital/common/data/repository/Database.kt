package org.covital.common.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.covital.common.data.datasource.daos.*
import org.covital.common.data.utils.RoomConverters
import org.covital.common.domain.entities.*

@Database(entities = [
    Diagnosis::class,
    Recording::class,
    User::class
], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun diagnosis(): DiagnosisDao
    abstract fun recording(): RecordingDao
    abstract fun user(): UserProfileDao
}
