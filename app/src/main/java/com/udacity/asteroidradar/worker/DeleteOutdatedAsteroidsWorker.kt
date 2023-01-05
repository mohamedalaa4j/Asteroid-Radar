package com.udacity.asteroidradar.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class DeleteOutdatedAsteroidsWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private val database = AsteroidDatabase.getInstance(applicationContext).asteroidDAO

    companion object {
        const val WORK_NAME = "DeleteOutdatedAsteroidsWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            database.clearBefore(todayDate())
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun todayDate(): String {
        val calendar = Calendar.getInstance()
        val todayDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(todayDate)
    }

}