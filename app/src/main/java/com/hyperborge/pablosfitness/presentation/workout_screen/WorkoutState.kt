package com.hyperborge.pablosfitness.presentation.workout_screen

import com.hyperborge.pablosfitness.data.local.model.DistanceUnit
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.WeightUnit
import com.hyperborge.pablosfitness.presentation.util.InputFieldState
import java.time.OffsetDateTime
import kotlin.time.Duration

data class WorkoutState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorText: String? = "",
    val workoutId: Int? = null,
    val exercise: Exercise,
    val date: OffsetDateTime = OffsetDateTime.now(),
    val weight: InputFieldState<Double> = InputFieldState(0.0, label = "Weight"),
    val weightUnit: InputFieldState<String> = InputFieldState(
        value = WeightUnit.KG.toString(),
        label = "Unit"
    ),
    val reps: InputFieldState<Int> = InputFieldState(0, label = "Reps"),
    val distance: InputFieldState<Double> = InputFieldState(0.0, label = "Distance"),
    val distanceUnit: InputFieldState<String> = InputFieldState(
        value = DistanceUnit.M.toString(),
        label = "Unit"
    ),
    val duration: InputFieldState<Duration> = InputFieldState(Duration.ZERO, label = "Duration"),

    val weightUnits: List<WeightUnit> = WeightUnit.values().toList(),
    val distanceUnits: List<DistanceUnit> = DistanceUnit.values().toList(),
)
