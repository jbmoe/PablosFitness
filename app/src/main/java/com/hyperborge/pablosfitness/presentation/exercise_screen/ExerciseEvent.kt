package com.hyperborge.pablosfitness.presentation.exercise_screen

import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.local.model.ExerciseType

sealed interface ExerciseEvent {
    object SaveExercise : ExerciseEvent
    data class NameEntered(val value: String) : ExerciseEvent
    data class CategoryPicked(val value: ExerciseCategory) : ExerciseEvent
    data class TypePicked(val value: ExerciseType) : ExerciseEvent
}