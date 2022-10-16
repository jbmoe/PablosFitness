package com.hyperborge.pablosfitness.common

import com.hyperborge.pablosfitness.data.local.model.*
import java.time.OffsetDateTime
import java.time.ZoneOffset

object TestData {
    private val weightRepActivities = listOf(
        Activity(
            id = 1,
            name = "Leg Press",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 2,
            name = "Chest Press",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 3,
            name = "Vertical Traction",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 4,
            name = "Pec Fly",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 5,
            name = "Rear Delt",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 6,
            name = "Upper Back",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 7,
            name = "Abduction",
            type = ActivityType.WeightAndReps
        ),
        Activity(
            id = 8,
            name = "Adduction",
            type = ActivityType.WeightAndReps
        )
    )
    private val distTimeActivities = listOf(
        Activity(
            id = 9,
            name = "Stationary Bike",
            type = ActivityType.DistanceAndTime
        ),
        Activity(
            id = 10,
            name = "Treadmill",
            type = ActivityType.DistanceAndTime
        )
    )
    private val exercises = listOf(
        Exercise(
            id = 1,
            sessionId = 1,
            activityId = 9,
            distance = 5.0,
            distanceUnit = DistanceUnit.KM,
            timeInSeconds = 900,
        ),
        Exercise(
            id = 2,
            sessionId = 1,
            activityId = 10,
            distance = 5.0,
            distanceUnit = DistanceUnit.KM,
            timeInSeconds = 1800,
        ),
        Exercise(
            id = 3,
            sessionId = 1,
            activityId = 1,
            weight = 35.0,
            weightUnit = WeightUnit.KG,
            repsPrSet = 10,
            sets = 3
        ),
        Exercise(
            id = 4,
            sessionId = 1,
            activityId = 2,
            weight = 45.0,
            weightUnit = WeightUnit.KG,
            repsPrSet = 12,
            sets = 3
        ),
        Exercise(
            id = 5,
            sessionId = 1,
            activityId = 3,
            weight = 30.0,
            weightUnit = WeightUnit.KG,
            repsPrSet = 10,
            sets = 4
        ),
        Exercise(
            id = 6,
            sessionId = 1,
            activityId = 4,
            weight = 55.0,
            weightUnit = WeightUnit.KG,
            repsPrSet = 8,
            sets = 2
        )
    )
    private val sessions = listOf(
        Session(
            id = 1,
            start = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond(),
            end = OffsetDateTime.now(ZoneOffset.UTC).plusHours(1).toEpochSecond()
        )
    )

    fun weightActivities(): List<Activity> = weightRepActivities
    fun distanceActivities(): List<Activity> = distTimeActivities
    fun exercises(): List<Exercise> = exercises
    fun sessions(): List<Session> = sessions
}