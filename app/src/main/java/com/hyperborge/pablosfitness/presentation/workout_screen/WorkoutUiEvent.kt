package com.hyperborge.pablosfitness.presentation.workout_screen

sealed interface WorkoutUiEvent {
    object WorkoutSaved : WorkoutUiEvent
}