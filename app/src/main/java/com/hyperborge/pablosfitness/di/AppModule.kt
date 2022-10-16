package com.hyperborge.pablosfitness.di

import android.app.Application
import androidx.room.Room
import com.hyperborge.pablosfitness.data.local.PablosFitnessDatabase
import com.hyperborge.pablosfitness.data.repository.DbRepository
import com.hyperborge.pablosfitness.data.repository.DbRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): PablosFitnessDatabase {
        return Room.databaseBuilder(
            app,
            PablosFitnessDatabase::class.java,
            PablosFitnessDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesDbRepository(db: PablosFitnessDatabase): DbRepository {
        return DbRepositoryImpl(db.activityDao, db.sessionDao, db.exerciseDao)
    }
}