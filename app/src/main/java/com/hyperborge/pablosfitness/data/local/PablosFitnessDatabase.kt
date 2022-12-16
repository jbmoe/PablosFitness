package com.hyperborge.pablosfitness.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyperborge.pablosfitness.data.local.dao.ExerciseDao
import com.hyperborge.pablosfitness.data.local.dao.WorkoutDao
import com.hyperborge.pablosfitness.data.local.model.Exercise
import com.hyperborge.pablosfitness.data.local.model.Workout

@Database(
    entities = [Workout::class, Exercise::class],
    version = 4
)
abstract class PablosFitnessDatabase : RoomDatabase() {
    abstract val exerciseDao: ExerciseDao
    abstract val workoutDao: WorkoutDao

    companion object {
        const val DATABASE_NAME = "pablosfitness_db"
    }
}