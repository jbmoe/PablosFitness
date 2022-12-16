package com.hyperborge.pablosfitness.presentation.workout_screen

import com.hyperborge.pablosfitness.data.local.model.DistanceUnit
import com.hyperborge.pablosfitness.data.local.model.WeightUnit
import kotlin.time.Duration

sealed interface WorkoutEvent {
    object SaveWorkout : WorkoutEvent
    object ClearValues : WorkoutEvent

    data class WeightChanged(val weight: Double) : WorkoutEvent
    data class RepsChanged(val reps: Int) : WorkoutEvent
    data class DistanceChanged(val distance: Double) : WorkoutEvent
    data class DurationChanged(val duration: Duration) : WorkoutEvent
    data class WeighUnitChanged(val item: WeightUnit) : WorkoutEvent
    data class DistanceUnitChanged(val item: DistanceUnit) : WorkoutEvent
}