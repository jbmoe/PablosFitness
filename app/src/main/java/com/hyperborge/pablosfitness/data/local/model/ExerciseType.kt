package com.hyperborge.pablosfitness.data.local.model

enum class ExerciseType {
    WeightAndReps,
    DistanceAndTime;

    override fun toString(): String {
        return when (this) {
            WeightAndReps -> "Weight and Reps"
            DistanceAndTime -> "Distance and Time"
        }
    }
}