package com.hyperborge.pablosfitness.data.local.model

data class WorkoutStats(
    val totalWorkouts: Int,
    val totalReps: Int?,
    val maxReps: StatsWorkoutRef<Int>?,
    val totalVolume: Double?,
    val maxVolume: StatsWorkoutRef<Double>?,
    val workoutVolume: StatsWorkoutRef<Double>?,
    val maxWeight: StatsWorkoutRef<Double>?,
    val weightUnit: WeightUnit?,
    val totalDistance: Double?,
    val maxDistance: StatsWorkoutRef<Double>?,
    val totalTime: Int?,
    val maxTime: StatsWorkoutRef<Int>?,
    val maxSpeed: StatsWorkoutRef<Double>?,
    val workoutSpeed: StatsWorkoutRef<Double>?,
    val maxPace: StatsWorkoutRef<Int>?,
    val workoutPace: StatsWorkoutRef<Int>?,
    val distanceUnit: DistanceUnit?
)

data class StatsWorkoutRef<T>(val value: T, val workout: WorkoutWithExercise)
