package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDAO {

    @Insert
    suspend fun insert(asteroid: AsteroidEntity)

//    @Update
//    suspend fun update(night: SleepNight)
//
//    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
//    suspend fun get(key: Long): SleepNight?
//
//    @Query("DELETE FROM daily_sleep_quality_table")
//    suspend fun clear()
//
//    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
//    fun getAllNights(): LiveData<List<SleepNight>>
//
//    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
//    suspend fun getTonight(): SleepNight?
}