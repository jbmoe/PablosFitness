package com.hyperborge.pablosfitness.presentation.workouts_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateCircusComponent(
    modifier: Modifier = Modifier,
    date: LocalDateTime,
    onResetToToday: () -> Unit,
    onPrevDateClicked: () -> Unit,
    onNextDayClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(modifier = Modifier.weight(1f), onClick = onPrevDateClicked) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }

        val text = if (date.toLocalDate() == LocalDate.now()) {
            stringResource(id = R.string.today)
        } else if (date.toLocalDate() == LocalDate.now().minusDays(1)) {
            stringResource(id = R.string.yesterday)
        } else if (date.toLocalDate() == LocalDate.now().plusDays(1)) {
            stringResource(id = R.string.tomorrow)
        } else {
            date.format(DateTimeFormatter.ofPattern("eee dd MMM"))
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .clickable { onResetToToday() },
            text = text,
            textAlign = TextAlign.Center
        )

        IconButton(modifier = Modifier.weight(1f), onClick = onNextDayClicked) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    PablosFitnessTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DateCircusComponent(
                modifier = Modifier.fillMaxWidth(),
                date = LocalDateTime.now(),
                onResetToToday = {},
                onPrevDateClicked = {},
                onNextDayClicked = {}
            )
        }
    }
}