package com.hyperborge.pablosfitness.data.local.model

enum class ExerciseCategory {
    Abs,
    Back,
    Biceps,
    Cardio,
    Chest,
    Legs,
    Shoulders,
    Triceps;

    companion object {
        fun getAll(): List<ExerciseCategory> {
            return values().toList()
        }
    }
}