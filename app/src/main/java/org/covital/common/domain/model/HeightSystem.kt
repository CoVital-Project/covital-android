package org.covital.common.domain.model

import java.lang.IllegalArgumentException

enum class HeightSystem(private val unitFormat: String) {
    FeetAndInches("%d'%d\""),
    Centimeters("%dcm");

    fun applyFormat(vararg value: Int): String {
        if (value.isEmpty()) throw IllegalArgumentException("Must provide some value")
        return unitFormat.format(value)
    }

    companion object {
        @JvmStatic fun fromId(id: Int): HeightSystem {
            return values().firstOrNull { it.ordinal == id } ?: Centimeters
        }
    }
}
