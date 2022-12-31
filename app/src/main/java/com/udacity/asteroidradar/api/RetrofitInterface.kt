package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.IMAGE_OF_THE_DAY
import com.udacity.asteroidradar.Constants.NEO_FEED
import com.udacity.asteroidradar.ImageOfTheDayModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET(NEO_FEED)
    suspend fun getNeoFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ResponseBody

    @GET(IMAGE_OF_THE_DAY)
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<ImageOfTheDayModel>

}