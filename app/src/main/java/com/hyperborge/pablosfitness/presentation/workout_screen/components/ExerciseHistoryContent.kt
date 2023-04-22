package com.hyperborge.pablosfitness.presentation.workout_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.workout_screen.WorkoutEvent
import com.hyperborge.pablosfitness.presentation.workout_screen.WorkoutState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseHistoryContent(
    padding: PaddingValues,
    state: WorkoutState,
    onEdit: (WorkoutPresentationModel) -> Unit,
    onAddWorkout: () -> Unit,
    onEvent: (WorkoutEvent) -> Unit
) {
    if (state.history.isEmpty()) {

        EmptyContent(
            topText = stringResource(id = R.string.no_exercise_history),
            bottomText = stringResource(id = R.string.start_new_workout),
            padding = padding,
            onButtonClick = onAddWorkout
        )
        return
    }

    val historyByDate = state.history.groupBy {
        it.createdAt
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        historyByDate.forEach { (date, workouts) ->
            if (workouts.isNotEmpty()) {
                stickyHeader {
                    WorkoutHistoryDate(date = date)
                }
                itemsIndexed(items = workouts) { index, workout ->
                    WorkoutHistoryItem(
                        modifier = Modifier.padding(start = 16.dp),
                        workout = workout,
                        onEdit = { onEdit(workout) },
                        onCopy = {
                            onEvent(WorkoutEvent.CopyWorkout(workout))
                        }
                    )
                    if (index != workouts.lastIndex) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            thickness = Dp.Hairline
                        )
                    }
                }
            }
        }
    }
}