package com.hyperborge.pablosfitness.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithExercise(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "id"
    )
    val exercise: Exercise
)