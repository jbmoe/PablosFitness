package com.hyperborge.pablosfitness.presentation.workout_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyperborge.pablosfitness.R
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.presentation.workout_screen.WorkoutEvent
import com.hyperborge.pablosfitness.presentation.workout_screen.WorkoutState
import java.time.format.DateTimeFormatter
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun ExerciseStatsContent(
    padding: PaddingValues,
    state: WorkoutState,
    onAddWorkout: () -> Unit,
    onGoToWorkout: (Int) -> Unit
) {
    val stats = state.workoutStats ?: return EmptyContent(
        topText = stringResource(id = R.string.no_exercise_stats),
        bottomText = stringResource(id = R.string.start_new_workout),
        padding = padding,
        onButtonClick = onAddWorkout
    )

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        columns = GridCells.Fixed(2),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        item {
            ExerciseStatsGridItem(
                value = stats.totalWorkouts.toString(),
                overLineText = "Total Workouts"
            )
        }
        when (state.exercise.type) {
            ExerciseType.WeightAndReps -> {
                stats.totalReps?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = it.toString(),
                            overLineText = "Total Reps"
                        )
                    }
                }
                stats.totalVolume?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "$it ${stats.weightUnit}",
                            overLineText = "Total Volume"
                        )
                    }
                }
                stats.workoutVolume?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "${it.value} ${stats.weightUnit}",
                            overLineText = "Workout Volume",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            )
                        )
                    }
                }
                stats.maxWeight?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "${it.value} ${stats.weightUnit}",
                            overLineText = "Max Weight",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.maxReps?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "${it.value}",
                            overLineText = "Max Reps",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.maxVolume?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "${it.value} ${stats.weightUnit}",
                            overLineText = "Max Volume",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
            }
            ExerciseType.DistanceAndTime -> {
                stats.totalDistance?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "$it ${stats.distanceUnit}",
                            overLineText = "Total Distance"
                        )
                    }
                }
                stats.totalTime?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = it.toDuration(DurationUnit.SECONDS).toString(),
                            overLineText = "Total Time"
                        )
                    }
                }
                stats.maxDistance?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = "${it.value} ${stats.distanceUnit}",
                            overLineText = "Max Distance",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.maxTime?.let {
                    item {
                        ExerciseStatsGridItem(
                            value = it.value.toDuration(DurationUnit.SECONDS).toString(),
                            overLineText = "Max Time",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.maxSpeed?.let {
                    item {
                        val speed = String.format("%.2f km/h", it.value)
                        ExerciseStatsGridItem(
                            value = speed,
                            overLineText = "Max Speed",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.workoutSpeed?.let {
                    item {
                        val speed = String.format("%.2f km/h", it.value)
                        ExerciseStatsGridItem(
                            value = speed,
                            overLineText = "Workout Speed",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            )
                        )
                    }
                }
                stats.maxPace?.let {
                    item {
                        val minutes = String.format("%02d", it.value / 60)
                        val seconds = String.format("%02d", it.value % 60)
                        ExerciseStatsGridItem(
                            value = "$minutes:$seconds /km",
                            overLineText = "Max Pace",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            ),
                            tooltipText = "Go to workout"
                        ) {
                            onGoToWorkout(it.workout.workout.id!!)
                        }
                    }
                }
                stats.workoutPace?.let {
                    item {
                        val minutes = String.format("%02d", it.value / 60)
                        val seconds = String.format("%02d", it.value % 60)
                        ExerciseStatsGridItem(
                            value = "$minutes:$seconds /km",
                            overLineText = "Workout Pace",
                            underLineText = it.workout.workout.createdAt.format(
                                DateTimeFormatter.ofPattern(
                                    "eee dd MMM"
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}