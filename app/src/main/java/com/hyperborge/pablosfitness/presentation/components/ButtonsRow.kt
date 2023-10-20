package com.hyperborge.pablosfitness.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    positiveText: String,
    onPositiveAction: () -> Unit,
    negativeText: String,
    onNegativeAction: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilledTonalButton(
            onClick = onPositiveAction,
            shape = OutlinedTextFieldDefaults.shape
        ) {
            Text(text = positiveText)
        }

        OutlinedButton(
            onClick = onNegativeAction,
            shape = OutlinedTextFieldDefaults.shape
        ) {
            Text(text = negativeText)
        }
    }
}