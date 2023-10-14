package com.hyperborge.pablosfitness.data.local.dao

import androidx.room.*
import com.hyperborge.pablosfitness.data.local.model.Workout
import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM workout WHERE id = :id")
    fun getWorkout(id: Int): Flow<WorkoutWithExercise>

    @Transaction
    @Query("SELECT * FROM workout WHERE datetime(:from) <= datetime(created_at) AND datetime(created_at) <= datetime(:to)")
    fun getWorkoutsInDateRange(
        from: OffsetDateTime,
        to: OffsetDateTime
    ): Flow<List<WorkoutWithExercise>>

    @Transaction
    @Query("SELECT * FROM workout WHERE id IN(:ids)")
    fun getWorkoutsByIds(ids: List<Int>): Flow<List<WorkoutWithExercise>>

    @Transaction
    @Query("SELECT * FROM workout WHERE exercise_id = :exerciseId ORDER BY datetime(created_at) DESC LIMIT 1")
    fun getNewestWorkoutWithExercise(exerciseId: Int): WorkoutWithExercise?

    @Transaction
    @Query("SELECT * FROM workout WHERE exercise_id = :exerciseId ORDER BY datetime(created_at) DESC")
    fun getWorkoutsWithExercise(exerciseId: Int): Flow<List<WorkoutWithExercise>>

    @Query("DELETE FROM workout WHERE exercise_id = :exerciseId")
    fun deleteWorkoutsWithExercise(exerciseId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: Workout)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkouts(workouts: List<Workout>)

    @Delete
    fun deleteWorkout(workout: Workout)

    @Delete
    fun deleteWorkouts(workouts: List<Workout>)
}