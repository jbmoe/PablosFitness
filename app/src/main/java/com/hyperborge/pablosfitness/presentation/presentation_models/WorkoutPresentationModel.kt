package com.hyperborge.pablosfitness.presentation.presentation_models

import com.hyperborge.pablosfitness.data.local.model.DistanceUnit
import com.hyperborge.pablosfitness.data.local.model.ExerciseType
import com.hyperborge.pablosfitness.data.local.model.WeightUnit
import kotlin.time.Duration

data class WorkoutPresentationModel(
    val id: Int,
    val exerciseName: String,
    val exerciseType: ExerciseType,
    val distance: Double? = null,
    val distanceUnit: DistanceUnit? = null,
    val duration: Duration? = null,
    val weight: Double? = null,
    val weightUnit: WeightUnit? = null,
    val reps: Int? = null,
    val isMarked: Boolean = false
)