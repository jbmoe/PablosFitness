package com.hyperborge.pablosfitness.presentation.exercises_screen

import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel

data class ExercisesState(
    val workoutDate: Long = 0,
    val exercises: Map<ExerciseCategory, List<ExercisePresentationModel>>,
    val expandedExerciseCategoriesMap: MutableMap<ExerciseCategory, Boolean>
)
