package com.hyperborge.pablosfitness.presentation.exercises_screen

import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel

sealed interface ExercisesUiEvent {
    data class ExerciseDeleted(val exercise: ExercisePresentationModel) : ExercisesUiEvent
}