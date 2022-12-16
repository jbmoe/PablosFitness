package com.hyperborge.pablosfitness.domain.extensions

import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.presentation.presentation_models.ExercisePresentationModel

object ExerciseExtensions {
    fun Exercise.mapToPresentationModel(): ExercisePresentationModel =
        ExercisePresentationModel(
            id = this.id!!,
            name = this.name,
            category = this.category,
            type = this.type
        )


    fun List<Exercise>.mapToPresentationModel(): List<ExercisePresentationModel> =
        this.map { it.mapToPresentationModel() }
}
