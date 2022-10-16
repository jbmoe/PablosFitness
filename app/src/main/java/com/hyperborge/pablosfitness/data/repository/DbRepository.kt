package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.model.Activity
import com.hyperborge.pablosfitness.data.local.model.ExerciseAndActivity
import com.hyperborge.pablosfitness.data.local.model.SessionWithExercisesAndActivities

interface DbRepository {
    suspend fun getSessions(): List<SessionWithExercisesAndActivities>

    fun getSessionById(id: Int): SessionWithExercisesAndActivities

    fun insertSession(session: SessionWithExercisesAndActivities)

    fun deleteSession(session: SessionWithExercisesAndActivities)

    fun getActivities(): List<Activity>

    fun getActivityById(id: Int): Activity

    fun insertActivity(activity: Activity)

    fun insertActivities(activities: List<Activity>)

    fun deleteActivity(activity: Activity)

    fun getExercisesForSession(sessionId: Int): List<ExerciseAndActivity>

    fun getExercise(id: Int): ExerciseAndActivity

    fun insertExercise(exercise: ExerciseAndActivity)

    fun deleteExercise(exercise: ExerciseAndActivity)
}