package com.hyperborge.pablosfitness.data.local

import androidx.room.*
import com.hyperborge.pablosfitness.data.local.model.Activity
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.Session

@Database(
    entities = [Activity::class, Exercise::class, Session::class],
    version = 1
)
abstract class PablosFitnessDatabase : RoomDatabase() {
    abstract val exerciseDao: ExerciseDao
    abstract val activityDao: ActivityDao
    abstract val sessionDao: SessionDao

    companion object {
        const val DATABASE_NAME = "pablosfitness_db"
    }
}

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise WHERE session_id = :sessionId")
    fun getExercisesForSession(sessionId: Int): List<Exercise>

    @Query("SELECT * FROM exercise WHERE id = :id")
    fun getExercise(id: Int): Exercise

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercises(exercises: List<Exercise>)

    @Delete
    fun deleteExercise(exercise: Exercise)
}

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity")
    fun getActivities(): List<Activity>

    @Query("SELECT * FROM activity WHERE id = :id")
    fun getActivity(id: Int): Activity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(activity: Activity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivities(activities: List<Activity>)

    @Delete
    fun deleteActivity(activity: Activity)
}

@Dao
interface SessionDao {
    @Query("SELECT * FROM session")
    fun getSessions(): List<Session>

    @Query("SELECT * FROM session WHERE id = :id")
    fun getSession(id: Int): Session

    @Transaction
    @Insert
    fun insertSession(session: Session, exercises: List<Exercise>)

    @Delete
    fun deleteSession(session: Session, exercises: List<Exercise>)
}