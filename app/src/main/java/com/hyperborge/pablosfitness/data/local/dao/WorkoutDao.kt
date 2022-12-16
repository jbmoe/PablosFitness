package com.hyperborge.pablosfitness.data.local.dao

import androidx.room.*
import com.hyperborge.pablosfitness.data.local.model.Workout
import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM workout WHERE id = :id")
    fun getWorkout(id: Int): Flow<WorkoutWithExercise>

    @Transaction
    @Query("SELECT * FROM workout WHERE :from <= created_at AND created_at <= :to")
    fun getWorkoutsInDateRange(from: Long, to: Long): Flow<List<WorkoutWithExercise>>

    @Transaction
    @Query("SELECT * FROM workout WHERE id IN(:ids)")
    fun getWorkoutsByIds(ids: List<Int>): Flow<List<WorkoutWithExercise>>

    @Transaction
    @Query("SELECT * FROM workout WHERE exercise_id = :exerciseId ORDER BY created_at DESC LIMIT 1")
    fun getNewestWorkoutWithExercise(exerciseId: Int): WorkoutWithExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: Workout)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkouts(workouts: List<Workout>)

    @Delete
    fun deleteWorkout(workout: Workout)

    @Delete
    fun deleteWorkouts(workouts: List<Workout>)
}