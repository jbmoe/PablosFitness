package com.hyperborge.pablosfitness.presentation.workouts_screen

import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import java.time.OffsetDateTime

data class WorkoutsState(
    val date: OffsetDateTime,
    val workouts: List<WorkoutPresentationModel> = emptyList()
) {
    val isMarking: Boolean = workouts.any { it.isMarked }
}
