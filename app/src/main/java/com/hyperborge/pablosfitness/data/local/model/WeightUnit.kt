package com.hyperborge.pablosfitness.data.local.model

enum class WeightUnit {
    KG, LB;

    override fun toString(): String = when (this) {
        KG -> "kg"
        LB -> "lb"
    }
}
