package com.hyperborge.pablosfitness.presentation.exercises_screen

import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel

sealed interface ExercisesEvent {
    data class DeleteExercise(val exercise: ExercisePresentationModel) : ExercisesEvent
    object RestoreDeletion : ExercisesEvent
}