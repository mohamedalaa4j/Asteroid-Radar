package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.udacity.asteroidradar.Constants.WEEK_END_DATE_DAYS
import com.udacity.asteroidradar.ImageOfTheDayModel
import com.udacity.asteroidradar.StateManagement
import com.udacity.asteroidradar.api.RetrofitObject
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val database: AsteroidDAO) : ViewModel() {

//    private val _asteroidList = MutableLiveData<ArrayList<Asteroid>>()
//    private val _asteroidList = MutableLiveData<Asteroid>()
//    val asteroidList: LiveData<Asteroid>
//        get() = _asteroidList

    private val _imageOfTheDay = MutableLiveData<ImageOfTheDayModel>()
    val imageOfTheDay: LiveData<ImageOfTheDayModel>
        get() = _imageOfTheDay

    private val _stateManagement = MutableLiveData<StateManagement>()

    val stateManagement: LiveData<StateManagement>
        get() = _stateManagement

    var asteroids: LiveData<List<Asteroid>>? = null

    private val _imageOfTheDayContentDescription= MutableLiveData<String>()
     val imageOfTheDayContentDescription: LiveData<String>
        get() = _imageOfTheDayContentDescription

    fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                _imageOfTheDay.postValue(RetrofitObject.retrofit.getImageOfTheDay().body())

                _imageOfTheDayContentDescription.postValue(RetrofitObject.retrofit.getImageOfTheDay().body()?.url)
            } catch (e: Exception) {
            }
        }
    }

    fun getNeoFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateManagement.postValue(StateManagement.LOADING)

            try {
                val responseBody = RetrofitObject.retrofit.getNeoFeed(todayDate(), endDayDate(DEFAULT_END_DATE_DAYS)).string()

                val jsonObject = JSONObject(responseBody)
                val asteroidList = parseAsteroidsJsonResult(jsonObject)

                _stateManagement.postValue(StateManagement.DONE)

                cacheAsteroidsDate(asteroidList)
            } catch (e: Exception) {
                Log.e("apiRequest", e.message.toString())
                _stateManagement.postValue(StateManagement.ERROR)
            }
        }
    }

    private suspend fun cacheAsteroidsDate(asteroidList: ArrayList<Asteroid>) {
        for (asteroid in asteroidList)
            database.insert(asteroid)
    }

    fun getSavedAsteroidsFromDB() {
        viewModelScope.launch {
            _stateManagement.postValue(StateManagement.LOADING)

            try {
                asteroids = database.getSavedAsteroids(todayDate())
                _stateManagement.postValue(StateManagement.DONE)
            } catch (e: Exception) {
                _stateManagement.postValue(StateManagement.ERROR)
            }
        }
    }

    fun getTodayAsteroidsFromDB() {
        viewModelScope.launch {
            _stateManagement.postValue(StateManagement.LOADING)

            try {
                asteroids = database.getTodayAsteroids(todayDate())
                Log.e("getTodayAsteroids",asteroids.toString() )
                _stateManagement.postValue(StateManagement.DONE)
            } catch (e: Exception) {
                _stateManagement.postValue(StateManagement.ERROR)
            }
        }
    }

    fun getWeekAsteroidsFromDB() {
        viewModelScope.launch {
            _stateManagement.postValue(StateManagement.LOADING)

            try {
                asteroids = database.getWeekAsteroids(todayDate(),endDayDate(WEEK_END_DATE_DAYS))
                Log.e("getWeekAsteroids",asteroids.toString() )
                _stateManagement.postValue(StateManagement.DONE)
            } catch (e: Exception) {
                _stateManagement.postValue(StateManagement.ERROR)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun todayDate(): String {
        val calendar = Calendar.getInstance()
        val todayDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(todayDate)
    }

    @SuppressLint("SimpleDateFormat")
    private fun endDayDate(period : Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, period)
        val tomorrowDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(tomorrowDate)
    }

    init {

        getNeoFeed()
        getImageOfTheDay()
        getSavedAsteroidsFromDB()
    }
}