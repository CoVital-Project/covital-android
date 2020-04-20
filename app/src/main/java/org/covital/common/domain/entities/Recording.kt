package org.covital.common.domain.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.covital.common.domain.model.*
import org.threeten.bp.Instant

@Entity(tableName = "recording", indices = [(Index(value = ["remoteId"], unique = true))])
data class Recording(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val localVideoUri: String,
    val remoteId: String? = null,
    val hgo2: Double,
    val bpm: Int,
    val created: Instant,
    val duration: Int)
