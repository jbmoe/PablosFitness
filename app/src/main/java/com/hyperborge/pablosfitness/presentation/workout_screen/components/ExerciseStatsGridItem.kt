package com.hyperborge.pablosfitness.presentation.workout_screen.components

import android.content.res.Configuration
import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.domain.extensions.OffsetDateTimeExtensions
import com.hyperborge.pablosfitness.domain.helpers.DateTimeHelper
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseStatsGridItem(
    modifier: Modifier = Modifier,
    value: String,
    overLineText: String,
    underLineText: String? = null,
    tooltipText: String? = null,
    onTooltipClicked: (() -> Unit)? = null
) {
    val tooltipState = remember { TooltipState() }
    val scope = rememberCoroutineScope()
    PlainTooltipBox(
        tooltip = {
            tooltipText?.let {
                TextButton(onClick = { onTooltipClicked?.invoke() }) {
                    Text(text = it)
                }
            }
        },
        tooltipState = tooltipState,
        shape = CardDefaults.shape,
        contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondaryContainer),
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Card(Modifier.clickable(false) {
            scope.launch { tooltipState.show() }
        }) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .height(112.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = overLineText, style = MaterialTheme.typography.labelMedium)
                Text(text = value, style = MaterialTheme.typography.labelLarge)
                underLineText?.let {
                    Text(text = underLineText, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            item {
                ExerciseStatsGridItem(
                    value = "596",
                    overLineText = "Total Reps",
                    underLineText = null
                )
            }
            item {
                ExerciseStatsGridItem(
                    value = "14.210 kg",
                    overLineText = "Total Volume"
                )
            }
            item {
                ExerciseStatsGridItem(
                    value = "45 kg",
                    overLineText = "Max Weight",
                    underLineText = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("eee dd MMM"))
                )
            }
            item {
                ExerciseStatsGridItem(
                    value = "40",
                    overLineText = "Max Reps",
                    underLineText = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("eee dd MMM"))
                )
            }
        }
    }
}