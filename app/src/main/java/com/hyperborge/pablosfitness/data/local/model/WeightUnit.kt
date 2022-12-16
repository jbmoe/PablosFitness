package com.hyperborge.pablosfitness.data.local.model

enum class WeightUnit {
    KG, LB;

    override fun toString(): String {
        return when (this) {
            KG -> "kgs"
            LB -> "lbs"
        }
    }
}
