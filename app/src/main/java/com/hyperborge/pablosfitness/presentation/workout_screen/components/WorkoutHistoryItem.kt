package com.hyperborge.pablosfitness.presentation.workout_screen.components

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutHistoryItem(
    modifier: Modifier = Modifier,
    workout: WorkoutPresentationModel,
    onEdit: () -> Unit,
    onCopy: () -> Unit,
) {
    var moreExpanded by remember { mutableStateOf(false) }
    var touchPoint by remember { mutableStateOf(DpOffset.Unspecified) }
    ListItem(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    touchPoint = DpOffset(x = it.x.toDp(), it.y.toDp())
                    moreExpanded = moreExpanded.not()
                }
            },
        headlineText = {
            Text(
                text = workout.exerciseName,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        supportingText = {
            ExerciseDetails(
                modifier = Modifier.fillMaxWidth(),
                workout = workout
            )
        }
    )
    DropdownMenu(
        offset = touchPoint,
        expanded = moreExpanded,
        onDismissRequest = { moreExpanded = false }
    ) {
        Log.d("DEBUGZZ", "${touchPoint.x}, ${touchPoint.y}")
        DropdownMenuItem(text = { Text(text = "Edit") }, onClick = {
            moreExpanded = false
            onEdit()
        })
        DropdownMenuItem(text = { Text(text = "Copy") }, onClick = {
            moreExpanded = false
            onCopy()
        })
    }
}

@Composable
private fun ExerciseDetails(modifier: Modifier = Modifier, workout: WorkoutPresentationModel) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.Start)
    ) {
        if (workout.distance != null &&
            workout.distanceUnit != null &&
            workout.duration != null
        ) {
            Text(text = "${workout.distance} ${workout.distanceUnit}")
            Text(text = workout.duration.toString())
        } else if (workout.weight != null && workout.reps != null) {
            Text(text = "${workout.weight} ${workout.weightUnit.toString()}")
            Text(text = "${workout.reps} reps")
        }
    }
}