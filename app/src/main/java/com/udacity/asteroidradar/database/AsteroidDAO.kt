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

    @Query("select * from asteroids_table ORDER BY closeApproachDate")
     fun getAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :todayDate")
     fun clear(todayDate:String)

}