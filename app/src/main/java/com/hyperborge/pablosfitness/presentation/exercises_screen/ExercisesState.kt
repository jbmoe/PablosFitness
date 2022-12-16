package com.hyperborge.pablosfitness.presentation.exercises_screen

import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel
import java.time.OffsetDateTime

data class ExercisesState(
    val workoutDate: OffsetDateTime = OffsetDateTime.now(),
    val exercises: Map<ExerciseCategory, List<ExercisePresentationModel>>,
    val expandedExerciseCategoriesMap: MutableMap<ExerciseCategory, Boolean>
)
