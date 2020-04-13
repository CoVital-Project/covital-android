package org.covital.common.domain.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.covital.common.domain.model.SkinTone
import org.threeten.bp.Instant

@Entity(tableName = "recording", indices = [(Index(value = ["remoteId"], unique = true))])
data class Recording(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val localVideoUri: String,
    val remoteId: String? = null,
    val spo2: Double,
    val height: Int,
    val weight: Int,
    val skinTone: SkinTone,
    val created: Instant,
    val duration: Int)
