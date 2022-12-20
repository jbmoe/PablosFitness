package com.hyperborge.pablosfitness.presentation.workout_screen

import com.hyperborge.pablosfitness.data.local.model.DistanceUnit
import com.hyperborge.pablosfitness.data.local.model.WeightUnit
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import kotlin.time.Duration

sealed interface WorkoutEvent {
    object SaveWorkout : WorkoutEvent
    object ClearValues : WorkoutEvent

    data class WeightChanged(val weight: String) : WorkoutEvent
    data class RepsChanged(val reps: String) : WorkoutEvent
    data class DistanceChanged(val distance: String) : WorkoutEvent
    data class DurationChanged(val duration: Duration) : WorkoutEvent
    data class WeighUnitChanged(val item: WeightUnit) : WorkoutEvent
    data class DistanceUnitChanged(val item: DistanceUnit) : WorkoutEvent
    data class CopyWorkout(val workout: WorkoutPresentationModel) : WorkoutEvent
}