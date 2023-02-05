package com.hyperborge.pablosfitness.presentation.workouts_screen

import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import java.time.OffsetDateTime

sealed interface WorkoutsEvent {
    object NextDate : WorkoutsEvent
    object PreviousDate : WorkoutsEvent
    object ResetDate : WorkoutsEvent
    object RestoreDeletion : WorkoutsEvent
    object MarkAllWorkouts : WorkoutsEvent
    object ClearMarkedWorkouts : WorkoutsEvent
    object DeleteMarkedWorkouts : WorkoutsEvent

    data class ToggleWorkoutMarked(val workout: WorkoutPresentationModel) : WorkoutsEvent
    data class GoToDate(val value: OffsetDateTime) : WorkoutsEvent
}

sealed interface WorkoutsUiEvent {
    data class WorkoutsDeleted(val workouts: List<WorkoutPresentationModel>) : WorkoutsUiEvent
}