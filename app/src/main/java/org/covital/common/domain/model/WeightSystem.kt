package org.covital.common.domain.model

import java.lang.IllegalArgumentException

enum class WeightSystem(private val unitFormat: String) {
    Pounds("%dlbs"),
    Kilograms("%dkg"),
    Stone("%dstn");

    fun applyFormat(vararg value: Double): String {
        if (value.isEmpty()) throw IllegalArgumentException("Must provide some value")
        return unitFormat.format(value)
    }

    companion object {
        @JvmStatic fun fromId(id: Int): WeightSystem {
            return values().firstOrNull { it.ordinal == id } ?: Kilograms
        }
    }
}
