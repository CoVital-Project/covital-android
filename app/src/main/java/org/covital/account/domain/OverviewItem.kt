package org.covital.account.domain

import org.covital.common.domain.model.*
import org.threeten.bp.Instant

sealed class OverviewItem : Comparable<OverviewItem> {
    data class DateOfBirth(val date: Instant?) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is DateOfBirth -> 0
                else -> -1
            }
        }
    }
    data class Gender(val value: org.covital.common.domain.model.Gender?) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is Gender -> 0
                else -> -1
            }
        }
    }
    data class Weight(val value: Double?, val system: WeightSystem = WeightSystem.Kilograms) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is Weight -> 0
                else -> -1
            }
        }
    }
    data class Height(val value: Int?, val system: HeightSystem = HeightSystem.Centimeters) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is Height -> 0
                else -> -1
            }
        }
    }
    data class CovidTest(val result: Boolean?) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is CovidTest -> 0
                else -> -1
            }
        }
    }
    data class Skin(val skinTone: SkinTone?) : OverviewItem() {
        override fun compareTo(other: OverviewItem): Int {
            return when (other) {
                is Skin -> 0
                else -> -1
            }
        }
    }
}
