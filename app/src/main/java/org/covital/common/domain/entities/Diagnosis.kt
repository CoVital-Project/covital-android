package org.covital.common.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "diagnosis")
data class Diagnosis(
    @PrimaryKey(autoGenerate = false) val name: String,
    val userId: String,
    val value: Boolean,
    val created: Instant,
    val updated: Instant = Instant.now(),
    val persisted: Boolean = false
)
