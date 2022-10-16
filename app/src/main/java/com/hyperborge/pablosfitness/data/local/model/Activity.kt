package com.hyperborge.pablosfitness.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: ActivityType
)

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "session_id") val sessionId: Int,
    @ColumnInfo(name = "activity_id") val activityId: Int,
    @ColumnInfo(name = "sets") val sets: Int? = null,
    @ColumnInfo(name = "reps_pr_set") val repsPrSet: Int? = null,
    @ColumnInfo(name = "weight") val weight: Double? = null,
    @ColumnInfo(name = "weight_unit") val weightUnit: WeightUnit? = null,
    @ColumnInfo(name = "distance") val distance: Double? = null,
    @ColumnInfo(name = "distance_unit") val distanceUnit: DistanceUnit? = null,
    @ColumnInfo(name = "time_in_seconds") val timeInSeconds: Int? = null,
)

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "start_time") val start: Long,
    @ColumnInfo(name = "end_time") val end: Long
)

data class ExerciseAndActivity(
    val activity: Activity,
    val exercise: Exercise
)

data class SessionWithExercisesAndActivities(
    val session: Session,
    val exercises: List<ExerciseAndActivity>
)