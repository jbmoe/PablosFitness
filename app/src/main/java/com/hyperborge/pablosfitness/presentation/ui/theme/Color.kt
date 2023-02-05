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
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF006B5E)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFF76F8E2)
val md_theme_light_onPrimaryContainer = Color(0xFF00201B)
val md_theme_light_secondary = Color(0xFF4A635E)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFCDE8E1)
val md_theme_light_onSecondaryContainer = Color(0xFF06201B)
val md_theme_light_tertiary = Color(0xFF446179)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFCAE6FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001E30)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFAFDFA)
val md_theme_light_onBackground = Color(0xFF191C1B)
val md_theme_light_surface = Color(0xFFFAFDFA)
val md_theme_light_onSurface = Color(0xFF191C1B)
val md_theme_light_surfaceVariant = Color(0xFFDAE5E1)
val md_theme_light_onSurfaceVariant = Color(0xFF3F4946)
val md_theme_light_outline = Color(0xFF6F7976)
val md_theme_light_inverseOnSurface = Color(0xFFEFF1EF)
val md_theme_light_inverseSurface = Color(0xFF2D3130)
val md_theme_light_inversePrimary = Color(0xFF55DBC6)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF006B5E)
val md_theme_light_outlineVariant = Color(0xFFBEC9C5)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF55DBC6)
val md_theme_dark_onPrimary = Color(0xFF003730)
val md_theme_dark_primaryContainer = Color(0xFF005047)
val md_theme_dark_onPrimaryContainer = Color(0xFF76F8E2)
val md_theme_dark_secondary = Color(0xFFB1CCC5)
val md_theme_dark_onSecondary = Color(0xFF1C3530)
val md_theme_dark_secondaryContainer = Color(0xFF334B46)
val md_theme_dark_onSecondaryContainer = Color(0xFFCDE8E1)
val md_theme_dark_tertiary = Color(0xFFACCAE5)
val md_theme_dark_onTertiary = Color(0xFF133348)
val md_theme_dark_tertiaryContainer = Color(0xFF2C4A60)
val md_theme_dark_onTertiaryContainer = Color(0xFFCAE6FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF191C1B)
val md_theme_dark_onBackground = Color(0xFFE0E3E1)
val md_theme_dark_surface = Color(0xFF191C1B)
val md_theme_dark_onSurface = Color(0xFFE0E3E1)
val md_theme_dark_surfaceVariant = Color(0xFF3F4946)
val md_theme_dark_onSurfaceVariant = Color(0xFFBEC9C5)
val md_theme_dark_outline = Color(0xFF899390)
val md_theme_dark_inverseOnSurface = Color(0xFF191C1B)
val md_theme_dark_inverseSurface = Color(0xFFE0E3E1)
val md_theme_dark_inversePrimary = Color(0xFF006B5E)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF55DBC6)
val md_theme_dark_outlineVariant = Color(0xFF3F4946)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF003F37)

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