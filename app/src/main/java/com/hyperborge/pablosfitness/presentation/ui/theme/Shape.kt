package com.hyperborge.pablosfitness.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
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
        val shapes = listOf(
            MaterialTheme.shapes.extraLarge to "extraLarge",
            MaterialTheme.shapes.large to "large",
            MaterialTheme.shapes.medium to "medium",
            MaterialTheme.shapes.small to "small",
            MaterialTheme.shapes.extraSmall to "extraSmall",
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(shapes) { (shape, text) ->
                Card(shape = shape) {
                    Text(text = text)
                }
            }
        }
    }
}