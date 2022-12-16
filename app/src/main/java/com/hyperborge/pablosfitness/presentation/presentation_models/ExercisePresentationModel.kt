package com.hyperborge.pablosfitness.presentation.presentation_models

import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.local.model.ExerciseType

data class ExercisePresentationModel(
    val id: Int,
    val name: String,
    val category: ExerciseCategory,
    val type: ExerciseType
)
