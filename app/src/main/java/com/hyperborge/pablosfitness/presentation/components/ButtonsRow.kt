package com.hyperborge.pablosfitness.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsRow(
    positiveText: String,
    onPositiveAction: () -> Unit,
    negativeText: String,
    onNegativeAction: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val buttonWidth = 98.dp
        FilledTonalButton(
            modifier = Modifier.width(buttonWidth),
            onClick = onPositiveAction,
            shape = TextFieldDefaults.outlinedShape
        ) {
            Text(text = positiveText)
        }

        Spacer(modifier = Modifier.width(84.dp))

        OutlinedButton(
            modifier = Modifier.width(buttonWidth),
            onClick = onNegativeAction,
            shape = TextFieldDefaults.outlinedShape
        ) {
            Text(text = negativeText)
        }
    }
}