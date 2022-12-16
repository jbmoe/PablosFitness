package com.hyperborge.pablosfitness.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

fun textOrNull(text: String?): @Composable (() -> Unit)? {
    return if (text.isNullOrBlank()) {
        null
    } else {
        { Text(text = text) }
    }
}

