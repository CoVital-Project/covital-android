package org.covital.common.domain.model

inline class BloodOxygen(val value: Double) {

    override fun toString(): String {
        return value.toInt().toString()
    }
}
