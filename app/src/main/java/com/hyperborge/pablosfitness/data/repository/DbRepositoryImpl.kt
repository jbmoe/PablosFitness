package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.dao.ExerciseDao
import com.hyperborge.pablosfitness.data.local.dao.WorkoutDao
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.WorkoutWithExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import java.time.OffsetDateTime
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) : DbRepository {
    override suspend fun getWorkouts(
        from: OffsetDateTime,
        to: OffsetDateTime
    ): Flow<List<WorkoutWithExercise>> {
        return workoutDao.getWorkoutsInDateRange(from, to).filterNotNull()
    }

    override suspend fun getNewestWorkoutWithExercise(exerciseId: Int): WorkoutWithExercise? {
        return workoutDao.getNewestWorkoutWithExercise(exerciseId)
    }

    override suspend fun getWorkout(id: Int): Flow<WorkoutWithExercise> {
        return workoutDao.getWorkout(id).filterNotNull()
    }

    override fun insertWorkout(workout: WorkoutWithExercise) {
        workoutDao.insertWorkout(workout.workout)
    }

    override fun insertWorkouts(workouts: List<WorkoutWithExercise>) {
        workoutDao.insertWorkouts(workouts.map { it.workout })
    }

    override suspend fun deleteWorkoutById(id: Int): WorkoutWithExercise {
        val workout = workoutDao.getWorkout(id).first()
        workoutDao.deleteWorkout(workout.workout)
        return workout
    }

    override suspend fun deleteWorkoutsByIds(ids: List<Int>): List<WorkoutWithExercise> {
        val workouts = workoutDao.getWorkoutsByIds(ids).first()
        workoutDao.deleteWorkouts(workouts = workouts.map { it.workout })
        return workouts
    }

    override suspend fun getExercise(id: Int): Flow<Exercise> {
        return exerciseDao.getExercise(id).filterNotNull()
    }

    override suspend fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.getExercises().filterNotNull()
    }

    override fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }

    override suspend fun deleteExerciseById(id: Int): Exercise {
        val exercise = exerciseDao.getExercise(id).first()
        workoutDao.deleteWorkoutsWithExercise(exerciseId = id)
        exerciseDao.deleteExercise(exercise)
        return exercise
    }
}