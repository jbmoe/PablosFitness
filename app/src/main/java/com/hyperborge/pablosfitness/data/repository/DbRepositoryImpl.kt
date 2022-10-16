package com.hyperborge.pablosfitness.data.repository

import com.hyperborge.pablosfitness.data.local.ActivityDao
import com.hyperborge.pablosfitness.data.local.ExerciseDao
import com.hyperborge.pablosfitness.data.local.SessionDao
import com.hyperborge.pablosfitness.data.local.model.Activity
import com.hyperborge.pablosfitness.data.local.model.ExerciseAndActivity
import com.hyperborge.pablosfitness.data.local.model.SessionWithExercisesAndActivities
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao,
    private val sessionDao: SessionDao,
    private val exerciseDao: ExerciseDao
) : DbRepository {
    override suspend fun getSessions(): List<SessionWithExercisesAndActivities> {
        val result = sessionDao.getSessions().map { session ->
            val exercises = getExercisesForSession(session.id!!)
            return@map SessionWithExercisesAndActivities(
                session = session,
                exercises = exercises
            )
        }

        return result
    }

    override fun getSessionById(id: Int): SessionWithExercisesAndActivities {
        val session = sessionDao.getSession(id)
        val exercises = getExercisesForSession(session.id!!)

        return SessionWithExercisesAndActivities(
            session = session,
            exercises = exercises
        )
    }

    override fun insertSession(session: SessionWithExercisesAndActivities) {
        sessionDao.insertSession(session.session, session.exercises.map { it.exercise })
    }

    override fun deleteSession(session: SessionWithExercisesAndActivities) {
        sessionDao.deleteSession(session.session, session.exercises.map { it.exercise })
    }

    override fun getActivities(): List<Activity> {
        return activityDao.getActivities()
    }

    override fun getActivityById(id: Int): Activity {
        return activityDao.getActivity(id)
    }

    override fun insertActivity(activity: Activity) {
        activityDao.insertActivity(activity)
    }

    override fun insertActivities(activities: List<Activity>) {
        activityDao.insertActivities(activities)
    }

    override fun deleteActivity(activity: Activity) {
        activityDao.deleteActivity(activity)
    }

    override fun getExercisesForSession(sessionId: Int): List<ExerciseAndActivity> {
        val result = exerciseDao.getExercisesForSession(sessionId).map { exercise ->
            val activity = getActivityById(exercise.activityId)
            return@map ExerciseAndActivity(
                exercise = exercise,
                activity = activity
            )
        }

        return result
    }

    override fun getExercise(id: Int): ExerciseAndActivity {
        val exercise = exerciseDao.getExercise(id)
        val activity = activityDao.getActivity(exercise.activityId)

        return ExerciseAndActivity(
            activity = activity,
            exercise = exercise
        )
    }

    override fun insertExercise(exercise: ExerciseAndActivity) {
        exerciseDao.insertExercise(exercise.exercise)
    }

    override fun deleteExercise(exercise: ExerciseAndActivity) {
        exerciseDao.deleteExercise(exercise.exercise)
    }
}