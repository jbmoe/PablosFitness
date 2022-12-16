package com.hyperborge.pablosfitness.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        val colors = listOf(
            MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary to "primary",
            MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer to "primaryContainer",
            MaterialTheme.colorScheme.inversePrimary to null to "inversePrimary",
            MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary to "secondary",
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer to "secondaryContainer",
            MaterialTheme.colorScheme.tertiary to MaterialTheme.colorScheme.onTertiary to "tertiary",
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer to "tertiaryContainer",
            MaterialTheme.colorScheme.background to MaterialTheme.colorScheme.onBackground to "background",
            MaterialTheme.colorScheme.surface to MaterialTheme.colorScheme.onSurface to "surface",
            MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant to "surfaceVariant",
            MaterialTheme.colorScheme.surfaceTint to null to "surfaceTint",
            MaterialTheme.colorScheme.inverseSurface to MaterialTheme.colorScheme.inverseOnSurface to "inverseSurface",
            MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.onError to "error",
            MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer to "errorContainer",
            MaterialTheme.colorScheme.outline to null to "outline",
            MaterialTheme.colorScheme.outlineVariant to null to "outlineVariant",
            MaterialTheme.colorScheme.scrim to null to "scrim",
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(colors) { (colorss, name) ->
                ListItem(
                    headlineText = { Text(text = name) },
                    colors = ListItemDefaults.colors(
                        containerColor = colorss.first,
                        headlineColor = colorss.second ?: contentColorFor(
                            backgroundColor = colorss.first
                        )
                    )
                )
            }
        }
    }
}