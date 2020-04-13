package org.covital.common.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.covital.common.data.datasource.daos.RecordingDao
import org.covital.common.data.utils.RoomConverters
import org.covital.common.domain.entities.Recording

@Database(entities = [
    Recording::class
], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun recording(): RecordingDao
}
