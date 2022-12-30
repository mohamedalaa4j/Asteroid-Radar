package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitObject {

    private val moshi = Moshi.Builder().build()

    private val retrofitInitialization: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val retrofit: RetrofitInterface by lazy {
        retrofitInitialization.create(RetrofitInterface::class.java)
    }
}