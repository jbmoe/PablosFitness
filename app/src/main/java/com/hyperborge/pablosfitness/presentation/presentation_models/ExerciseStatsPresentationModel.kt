package com.hyperborge.pablosfitness.presentation.presentation_models

data class ExerciseStatsPresentationModel<T>(
    val value: T,
    val workout: WorkoutPresentationModel
)