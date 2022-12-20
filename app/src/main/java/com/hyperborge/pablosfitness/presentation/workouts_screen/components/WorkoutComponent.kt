package com.hyperborge.pablosfitness.presentation.workouts_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyperborge.pablosfitness.common.TestData
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.domain.extensions.WorkoutExtensions.mapToPresentationModel
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import com.hyperborge.pablosfitness.presentation.ui.theme.PablosFitnessTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WorkoutComponent(
    modifier: Modifier = Modifier,
    exercise: WorkoutPresentationModel,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    CardComponent(
        modifier = modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        ),
        isMarked = exercise.isMarked
    ) {
        ListItem(
            headlineText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = exercise.exerciseName)
                }
            },
            supportingText = {
                ExerciseDetails(
                    modifier = Modifier.fillMaxWidth(),
                    exercise = exercise
                )
            }
        )
    }
}

@Composable
private fun CardComponent(
    modifier: Modifier = Modifier,
    isMarked: Boolean,
    content: @Composable (ColumnScope.() -> Unit)
) {
    if (!isMarked) {
        ElevatedCard(
            modifier = modifier,
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.elevatedCardColors(),
            elevation = CardDefaults.elevatedCardElevation()
        ) { content() }
    } else {
        OutlinedCard(
            modifier = modifier,
            shape = CardDefaults.outlinedShape,
            colors = CardDefaults.outlinedCardColors(),
            elevation = CardDefaults.outlinedCardElevation()
        ) { content() }
    }
}

@Composable
private fun ExerciseDetails(modifier: Modifier = Modifier, exercise: WorkoutPresentationModel) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (
            exercise.distance != null &&
            exercise.distanceUnit != null &&
            exercise.duration != null
        ) {
            Text(text = "${exercise.distance} ${exercise.distanceUnit}")
            Text(text = exercise.duration.toString())
        } else if (
            exercise.weight != null &&
            exercise.weightUnit != null &&
            exercise.reps != null
        ) {
            Text(text = "${exercise.weight} ${exercise.weightUnit}")
            Text(text = "${exercise.reps} reps")
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun Preview() {
    val exercise1 = TestData
        .workoutsWithExercises()
        .mapToPresentationModel()
        .random()
    val exercise2 = TestData
        .workoutsWithExercises(ExerciseType.DistanceAndTime)
        .random()
        .mapToPresentationModel()

    PablosFitnessTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            WorkoutComponent(modifier = Modifier.fillMaxWidth(), exercise1, {}) {}
            WorkoutComponent(modifier = Modifier.fillMaxWidth(), exercise2, {}) {}
        }
    }
}