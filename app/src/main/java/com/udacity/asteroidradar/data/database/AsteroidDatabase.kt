package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.models.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDAO: AsteroidDAO

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AsteroidDatabase::class.java, "asteroids_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}