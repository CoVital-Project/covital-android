package org.covital.common.domain.model

enum class Gender {
    Female,
    Male;

    companion object {
        @JvmStatic fun fromId(id: Int): Gender {
            return values().firstOrNull { it.ordinal == id } ?: Female
        }
    }
}
