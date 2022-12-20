package com.hyperborge.pablosfitness.data.local.model

enum class DistanceUnit {
    KM, MI, Steps, M;

    override fun toString(): String = when (this) {
        KM -> "km"
        MI -> "mi"
        Steps -> "steps"
        M -> "m"
    }
}
