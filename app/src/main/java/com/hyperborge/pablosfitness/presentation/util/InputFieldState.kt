package com.hyperborge.pablosfitness.presentation.util

data class InputFieldState<T>(
    val value: T,
    val label: String? = null,
    val placeholder: T? = null,
    val error: String? = null,
    val isError: Boolean = false,
    val isEnabled: Boolean = true
) {
    fun setValueAndClearError(value: T): InputFieldState<T> {
        return this.copy(
            value = value,
            error = null,
            isError = false
        )
    }

    fun setError(error: String): InputFieldState<T> {
        return this.copy(
            error = error,
            isError = true
        )
    }
}
