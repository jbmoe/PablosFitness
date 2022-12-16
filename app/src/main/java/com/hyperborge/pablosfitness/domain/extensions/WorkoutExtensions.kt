package com.hyperborge.pablosfitness.domain.extensions

import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
import com.hyperborge.pablosfitness.presentation.presentation_models.WorkoutPresentationModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun WorkoutWithExercise.mapToPresentationModel(): WorkoutPresentationModel {
    return WorkoutPresentationModel(
        id = this.workout.id!!,
        exerciseName = this.exercise.name,
        exerciseType = this.exercise.type,
        distance = this.workout.distance,
        distanceUnit = this.workout.distanceUnit,
        duration = this.workout.timeInSeconds?.toDuration(DurationUnit.SECONDS),
        weight = this.workout.weight,
        weightUnit = this.workout.weightUnit,
        reps = this.workout.reps
    )
}

fun List<WorkoutWithExercise>.mapToPresentationModel(): List<WorkoutPresentationModel> {
    return this.map { it.mapToPresentationModel() }
}