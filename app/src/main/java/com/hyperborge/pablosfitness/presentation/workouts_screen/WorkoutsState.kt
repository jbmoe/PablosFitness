package com.hyperborge.pablosfitness.presentation.workouts_screen

import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import java.time.LocalDateTime

data class WorkoutsState(
    val date: LocalDateTime,
    val workouts: List<WorkoutPresentationModel> = emptyList()
) {
    val isMarking: Boolean = workouts.any { it.isMarked }
}
