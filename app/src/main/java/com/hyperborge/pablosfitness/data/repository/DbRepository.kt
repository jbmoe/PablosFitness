package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
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
    fun insertWorkout(workout: WorkoutWithExercise)
    fun insertWorkouts(workouts: List<WorkoutWithExercise>)
    suspend fun deleteWorkoutById(id: Int): WorkoutWithExercise
    suspend fun deleteWorkoutsByIds(ids: List<Int>): List<WorkoutWithExercise>
}

interface DbRepository : ExerciseRepository, WorkoutRepository