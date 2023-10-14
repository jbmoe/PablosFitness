package com.hyperborge.pablosfitness.domain.extensions

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.orMax(): Int {
    return this ?: Int.MAX_VALUE
}

fun Double?.orMax(): Double {
    return this ?: Double.MAX_VALUE
}