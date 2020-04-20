package org.covital.account.domain

import org.covital.common.domain.entities.Diagnosis
import org.threeten.bp.Instant

data class DiagnosisItem(
    val title: String,
    val checked: Boolean = false,
    val created: Instant = Instant.now()
) : Comparable<DiagnosisItem> {

    override fun compareTo(other: DiagnosisItem): Int {
        return title.compareTo(other.title)
    }

    companion object {
        @JvmStatic fun fromEntity(entity: Diagnosis): DiagnosisItem {
            return DiagnosisItem(
                entity.name,
                entity.value,
                entity.created
            )
        }
    }

}
