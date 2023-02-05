package com.hyperborge.pablosfitness.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        val types = listOf(
            MaterialTheme.typography.displayLarge to "displayLarge",
            MaterialTheme.typography.displayMedium to "displayMedium",
            MaterialTheme.typography.displaySmall to "displaySmall",
            MaterialTheme.typography.headlineLarge to "headlineLarge",
            MaterialTheme.typography.headlineMedium to "headlineMedium",
            MaterialTheme.typography.headlineSmall to "headlineSmall",
            MaterialTheme.typography.titleLarge to "titleLarge",
            MaterialTheme.typography.titleMedium to "titleMedium",
            MaterialTheme.typography.titleSmall to "titleSmall",
            MaterialTheme.typography.bodyLarge to "bodyLarge",
            MaterialTheme.typography.bodyMedium to "bodyMedium",
            MaterialTheme.typography.bodySmall to "bodySmall",
            MaterialTheme.typography.labelLarge to "labelLarge",
            MaterialTheme.typography.labelMedium to "labelMedium",
            MaterialTheme.typography.labelSmall to "labelSmall"
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(types) { (type, text) ->
                Text(text = text, style = type)
            }
        }
    }
}