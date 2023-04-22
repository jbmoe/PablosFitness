package com.hyperborge.pablosfitness.presentation.workout_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyContent(
    topText: String,
    bottomText: String,
    padding: PaddingValues,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = topText,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(onClick = onButtonClick) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = null
            )
        }
        Text(text = bottomText)
    }
}
