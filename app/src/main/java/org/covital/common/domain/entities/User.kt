package org.covital.common.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.covital.common.domain.model.HeightSystem
import org.covital.common.domain.model.Gender
import org.covital.common.domain.model.SkinTone
import org.covital.common.domain.model.WeightSystem
import org.threeten.bp.Instant

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false) val id: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val dateOfBirth: Instant?,
    val gender: Gender?,
    val weight: Double?,
    val weightSystem: WeightSystem,
    val height: Int?,
    val heightSystem: HeightSystem,
    val covid19TestedAt: Instant?,
    val covid19TestedResult: Boolean?,
    val skinTone: SkinTone?,
    val created: Instant,
    val updated: Instant,
    val persisted: Boolean
)
