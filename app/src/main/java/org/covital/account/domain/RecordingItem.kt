package org.covital.account.domain

import org.threeten.bp.Instant
import org.covital.common.domain.entities.Recording as RecordingEntity

sealed class RecordingItem : Comparable<RecordingItem> {
    object AddNewMeasurement : RecordingItem() {
        override fun compareTo(other: RecordingItem): Int {
            return when (other) {
                is AddNewMeasurement -> 0
                else -> -1
            }
        }
    }

    data class Recording(
        val id: Long,
        val hgo2: Int,
        val bpm: Int,
        val timestamp: Instant
    ) : RecordingItem() {
        override fun compareTo(other: RecordingItem): Int {
            if (other !is Recording) return 1
            if (id == id) return 0
            return timestamp.compareTo(other.timestamp)
        }
    }

    data class Error(val error: Throwable) : RecordingItem() {
        override fun compareTo(other: RecordingItem): Int {
            return when (other) {
                is Error -> 0
                else -> -1
            }
        }
    }

    companion object {
        @JvmStatic fun fromEntity(entity: RecordingEntity): Recording {
            return Recording(
                entity.id,
                entity.hgo2.toInt(),
                entity.bpm,
                entity.created)
        }
    }
}
