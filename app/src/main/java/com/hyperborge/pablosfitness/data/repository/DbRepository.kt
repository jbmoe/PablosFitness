package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.model.*
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

interface ExerciseRepository {
    suspend fun getExercise(id: Int): Flow<Exercise>
    suspend fun getExercises(): Flow<List<Exercise>>
    fun insertExercise(exercise: Exercise)
    suspend fun deleteExerciseById(id: Int): Exercise
}

interface WorkoutRepository {
    suspend fun getWorkout(id: Int): Flow<WorkoutWithExercise>
    suspend fun getWorkouts(
        from: OffsetDateTime,
        to: OffsetDateTime
    ): Flow<List<WorkoutWithExercise>>

    suspend fun getNewestWorkoutWithExercise(exerciseId: Int): WorkoutWithExercise?
    suspend fun getWorkoutsWithExercise(exerciseId: Int): Flow<List<WorkoutWithExercise>>
    fun insertWorkout(workout: WorkoutWithExercise)
    fun insertWorkouts(workouts: List<WorkoutWithExercise>)
    suspend fun deleteWorkoutById(id: Int): WorkoutWithExercise
    suspend fun deleteWorkoutsByIds(ids: List<Int>): List<WorkoutWithExercise>
}

interface DbRepository : ExerciseRepository, WorkoutRepository {
    suspend fun getWorkoutStats(
        exerciseId: Int,
        workoutId: Int? = null,
        weightUnit: WeightUnit,
        distanceUnit: DistanceUnit,
        from: OffsetDateTime,
        to: OffsetDateTime
    ): Flow<WorkoutStats?>
}