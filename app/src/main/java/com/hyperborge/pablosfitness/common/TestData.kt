package com.hyperborge.pablosfitness.common

import com.hyperborge.pablosfitness.data.local.model.*
import com.hyperborge.pablosfitness.domain.helpers.DateHelper
import java.time.LocalDateTime

object TestData {
    private val yesterday = LocalDateTime.now().minusDays(1)
    private val today = LocalDateTime.now()
    private val weightRepExercises = listOf(
        Exercise(
            id = 1,
            name = "Leg Press",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        ),
        Exercise(
            id = 2,
            name = "Chest Press",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Chest
        ),
        Exercise(
            id = 3,
            name = "Vertical Traction",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Back
        ),
        Exercise(
            id = 4,
            name = "Pec Fly",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Chest
        ),
        Exercise(
            id = 5,
            name = "Rear Delt",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Back
        ),
        Exercise(
            id = 6,
            name = "Upper Back",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Back
        ),
        Exercise(
            id = 7,
            name = "Abduction",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        ),
        Exercise(
            id = 8,
            name = "Adduction",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        ),
        Exercise(
            id = 9,
            name = "Leg Extension",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        ),
        Exercise(
            id = 10,
            name = "Leg Curl",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        ),
        Exercise(
            id = 11,
            name = "Standing Calf",
            type = ExerciseType.WeightAndReps,
            category = ExerciseCategory.Legs
        )
    )
    private val distTimeExercises = listOf(
        Exercise(
            id = 12,
            name = "Stationary Bike",
            type = ExerciseType.DistanceAndTime,
            category = ExerciseCategory.Cardio
        ),
        Exercise(
            id = 13,
            name = "Treadmill",
            type = ExerciseType.DistanceAndTime,
            category = ExerciseCategory.Cardio
        ),
        Exercise(
            id = 14,
            name = "Rowing Machine",
            type = ExerciseType.DistanceAndTime,
            category = ExerciseCategory.Cardio
        ),
        Exercise(
            id = 15,
            name = "Stairmaster",
            type = ExerciseType.DistanceAndTime,
            category = ExerciseCategory.Cardio
        )
    )
    private val workouts = listOf(
        Workout(
            id = 1,
            exerciseId = 12,
            distance = 5.0,
            distanceUnit = DistanceUnit.KM,
            timeInSeconds = 900,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(today),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        ),
        Workout(
            id = 2,
            exerciseId = 13,
            distance = 5.0,
            distanceUnit = DistanceUnit.KM,
            timeInSeconds = 1800,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(today),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        ),
        Workout(
            id = 3,
            exerciseId = 1,
            weight = 35.0,
            weightUnit = WeightUnit.KG,
            reps = 30,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(today),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        ),
        Workout(
            id = 4,
            exerciseId = 2,
            weight = 45.0,
            weightUnit = WeightUnit.KG,
            reps = 36,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(today),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        ),
        Workout(
            id = 5,
            exerciseId = 3,
            weight = 30.0,
            weightUnit = WeightUnit.KG,
            reps = 40,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(yesterday),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        ),
        Workout(
            id = 6,
            exerciseId = 4,
            weight = 55.0,
            weightUnit = WeightUnit.KG,
            reps = 16,
            createdAt = DateHelper.getEpochSecondsFromLocalDateTime(yesterday),
            updatedAt = DateHelper.getEpochSecondsFromLocalDateTime(today)
        )
    )

    fun weightExercises(): List<Exercise> = weightRepExercises
    fun distanceExercises(): List<Exercise> = distTimeExercises

    fun workoutsWithExercises(exerciseType: ExerciseType = ExerciseType.WeightAndReps): List<WorkoutWithExercise> {
        val exercises = weightRepExercises.union(distTimeExercises).filter {
            it.type == exerciseType
        }

        val workouts = workouts.filter { exercise ->
            exercises.any { activity ->
                activity.id == exercise.exerciseId
            }
        }

        return workouts.map { workout ->
            WorkoutWithExercise(
                exercise = exercises.first { exercise ->
                    exercise.id == workout.exerciseId
                },
                workout = workout
            )
        }
    }
}