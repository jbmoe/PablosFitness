package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.dao.ExerciseDao
import com.hyperborge.pablosfitness.data.local.dao.WorkoutDao
import com.hyperborge.pablosfitness.data.local.model.*
import com.hyperborge.pablosfitness.domain.extensions.*
import kotlinx.coroutines.flow.*
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.roundToInt

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

    override suspend fun getWorkoutsWithExercise(exerciseId: Int): Flow<List<WorkoutWithExercise>> {
        return workoutDao.getWorkoutsWithExercise(exerciseId)
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

    override suspend fun getWorkoutStats(
        exerciseId: Int,
        workoutId: Int?,
        weightUnit: WeightUnit,
        distanceUnit: DistanceUnit,
        from: OffsetDateTime,
        to: OffsetDateTime
    ): Flow<WorkoutStats?> {
        val stats: Flow<WorkoutStats?> =
            workoutDao.getWorkoutsWithExercise(exerciseId).map { workouts ->
                val totalWorkouts = workouts.size
                var totalReps: Int? = null
                var totalVolume: Double? = null
                var totalTime: Int? = null
                var totalDistance: Double? = null
                var maxReps: StatsWorkoutRef<Int>? = null
                var maxWeight: StatsWorkoutRef<Double>? = null
                var maxVolume: StatsWorkoutRef<Double>? = null
                var currentWorkoutVolume: StatsWorkoutRef<Double>? = null
                var maxDistance: StatsWorkoutRef<Double>? = null
                var maxTime: StatsWorkoutRef<Int>? = null
                var maxSpeed: StatsWorkoutRef<Double>? = null
                var maxPace: StatsWorkoutRef<Int>? = null
                var currentWorkoutSpeed: StatsWorkoutRef<Double>? = null
                var currentWorkoutPace: StatsWorkoutRef<Int>? = null

                for (workout in workouts) {
                    val isCurrentWorkout = workout.workout.id == workoutId
                    val workoutReps = workout.workout.reps
                    val workoutWeight = getWorkoutWeightInUnit(workout.workout, weightUnit)
                    val workoutVolume = workoutWeight?.let { workoutReps?.times(it) }
                    val workoutDistance = getWorkoutDistanceInUnit(workout.workout, distanceUnit)
                    val workoutTime = workout.workout.timeInSeconds
                    val workoutSpeed = workoutTime?.let { seconds ->
                        workoutDistance?.div(seconds.toDouble() / 60 / 60)
                    }
                    val workoutPace = workoutTime?.let { seconds ->
                        workoutDistance?.let { kilometers ->
                            (seconds.toDouble() / 60).div(kilometers)
                        }
                    }?.times(60)?.toInt()

                    workoutReps?.let {
                        totalReps = (totalReps ?: 0) + workoutReps
                        if (workoutReps > maxReps?.value.orZero()) {
                            maxReps = StatsWorkoutRef(workoutReps, workout)
                        }
                    }

                    workoutWeight?.let {
                        if (workoutWeight > maxWeight?.value.orZero()) {
                            maxWeight = StatsWorkoutRef(workoutWeight, workout)
                        }
                    }

                    workoutVolume?.let {
                        totalVolume = (totalVolume ?: 0.0) + workoutVolume

                        if (workoutVolume > maxVolume?.value.orZero()) {
                            maxVolume = StatsWorkoutRef(workoutVolume, workout)
                        }
                        if (isCurrentWorkout) {
                            currentWorkoutVolume = StatsWorkoutRef(workoutVolume, workout)
                        }
                    }

                    workoutDistance?.let {
                        totalDistance = (totalDistance ?: 0.0) + workoutDistance

                        if (workoutDistance > maxDistance?.value.orZero()) {
                            maxDistance = StatsWorkoutRef(workoutDistance, workout)
                        }
                    }

                    workoutTime?.let {
                        totalTime = (totalTime ?: 0) + workoutTime

                        if (workoutTime > maxTime?.value.orZero()) {
                            maxTime = StatsWorkoutRef(workoutTime, workout)
                        }
                    }

                    workoutSpeed?.let {
                        if (workoutSpeed > maxSpeed?.value.orZero()) {
                            maxSpeed = StatsWorkoutRef(workoutSpeed, workout)
                        }
                        if (isCurrentWorkout) {
                            currentWorkoutSpeed = StatsWorkoutRef(workoutSpeed, workout)
                        }
                    }

                    workoutPace?.let {
                        if (workoutPace < maxPace?.value.orMax()) {
                            maxPace = StatsWorkoutRef(workoutPace, workout)
                        }
                        if (isCurrentWorkout) {
                            currentWorkoutPace = StatsWorkoutRef(workoutPace, workout)
                        }
                    }
                }

                WorkoutStats(
                    totalWorkouts = totalWorkouts,
                    totalReps = totalReps,
                    totalVolume = totalVolume,
                    maxReps = maxReps,
                    maxVolume = maxVolume,
                    workoutVolume = currentWorkoutVolume,
                    maxWeight = maxWeight,
                    weightUnit = weightUnit,
                    totalDistance = totalDistance,
                    maxDistance = maxDistance,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    maxSpeed = maxSpeed,
                    workoutSpeed = currentWorkoutSpeed,
                    maxPace = maxPace,
                    workoutPace = currentWorkoutPace,
                    distanceUnit = distanceUnit
                )
            }

        return stats
    }

    private fun getWorkoutWeightInUnit(workout: Workout, weightUnit: WeightUnit): Double? {
        val weight = workout.weight ?: return null
        return if (workout.weightUnit == weightUnit) {
            weight
        } else when (weightUnit) {
            WeightUnit.KG -> weight.div(2.2046226218)
            WeightUnit.LB -> weight.times(2.2046226218)
        }
    }

    private fun getWorkoutDistanceInUnit(workout: Workout, distanceUnit: DistanceUnit): Double? {
        val distance = workout.distance ?: return null
        return when (workout.distanceUnit) {
            distanceUnit -> {
                distance
            }
            DistanceUnit.KM -> {
                when (distanceUnit) {
                    DistanceUnit.KM -> distance
                    DistanceUnit.MI -> distance / 0.621371
                    DistanceUnit.Steps -> distance * 1000
                    DistanceUnit.M -> distance * 1000
                }
            }
            DistanceUnit.MI -> {
                when (distanceUnit) {
                    DistanceUnit.KM -> distance * 1.609344
                    DistanceUnit.MI -> distance
                    DistanceUnit.Steps -> distance * 1609.344
                    DistanceUnit.M -> distance * 1609.344
                }
            }
            DistanceUnit.Steps -> {
                when (distanceUnit) {
                    DistanceUnit.KM -> distance / 1000
                    DistanceUnit.MI -> distance / 1609.344
                    DistanceUnit.Steps -> distance
                    DistanceUnit.M -> distance
                }
            }
            DistanceUnit.M -> {
                when (distanceUnit) {
                    DistanceUnit.KM -> distance / 1000
                    DistanceUnit.MI -> distance / 1609.344
                    DistanceUnit.Steps -> distance
                    DistanceUnit.M -> distance
                }
            }
        }
    }
}
