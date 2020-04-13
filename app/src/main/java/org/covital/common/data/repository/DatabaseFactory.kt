package org.covital.common.data.repository

import android.content.Context
import androidx.room.Room

object DatabaseFactory {
    fun create(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
