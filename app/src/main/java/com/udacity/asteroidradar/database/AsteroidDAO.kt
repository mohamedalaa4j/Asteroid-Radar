package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: Asteroid)

    @Query("select * from asteroids_table WHERE closeApproachDate >= :todayDate ORDER BY closeApproachDate")
     fun getAsteroids(todayDate: String): LiveData<List<Asteroid>>

    @Query("select * from asteroids_table WHERE closeApproachDate = :todayDate")
    fun getAsteroidsOfToday(todayDate: String): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :todayDate")
    suspend fun clearBefore(todayDate: String)

}