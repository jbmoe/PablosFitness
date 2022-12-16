package com.hyperborge.pablosfitness.presentation.exercise_screen

sealed interface ExerciseUiEvent {
    object ExerciseSaved : ExerciseUiEvent
}