package com.hyperborge.pablosfitness.presentation.exercise_screen

import com.hyperborge.pablosfitness.data.local.model.ExerciseCategory
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.presentation.util.InputFieldState

data class ExerciseState(
    val id: Int? = null,
    val categories: List<ExerciseCategory>,
    val types: List<ExerciseType>,
    val name: InputFieldState<String> = InputFieldState(
        value = "",
        label = "Name",
        placeholder = "Name of the exercise",
    ),
    val category: InputFieldState<String> = InputFieldState(
        value = "",
        label = "Category",
        placeholder = "Pick a category"
    ),
    val type: InputFieldState<String> = InputFieldState(
        value = "",
        label = "Type",
        placeholder = "Pick a type"
    )
)
