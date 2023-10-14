package com.hyperborge.pablosfitness.domain.extensions

object BooleanExtensions {
    fun Boolean.ifTrue(action: () -> Unit) {
        if (this) action()
    }
    fun Boolean.ifFalse(action: () -> Unit) {
        if (!this) action()
    }
}