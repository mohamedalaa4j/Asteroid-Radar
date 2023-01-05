package com.udacity.asteroidradar.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.api.RetrofitObject
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.utilities.Constants
import com.udacity.asteroidradar.utilities.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private val database = AsteroidDatabase.getInstance(applicationContext).asteroidDAO

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    override suspend fun doWork(): Result {
        val responseBody = RetrofitObject.retrofit.getNeoFeed(todayDate(), endDayDate(Constants.DEFAULT_END_DATE_DAYS)).string()

        val jsonObject = JSONObject(responseBody)
        val asteroidList = parseAsteroidsJsonResult(jsonObject)

        return try {
            cacheAsteroidsDate(asteroidList)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    private suspend fun cacheAsteroidsDate(asteroidList: ArrayList<Asteroid>) {
        for (asteroid in asteroidList)
            database.insert(asteroid)
    }

    @SuppressLint("SimpleDateFormat")
    private fun todayDate(): String {
        val calendar = Calendar.getInstance()
        val todayDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(todayDate)
    }

    @SuppressLint("SimpleDateFormat")
    private fun endDayDate(period: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, period)
        val tomorrowDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(tomorrowDate)
    }
}