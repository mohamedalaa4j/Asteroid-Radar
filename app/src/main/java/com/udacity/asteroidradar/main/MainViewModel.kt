package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
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
//    val asteroidList: LiveData<ArrayList<Asteroid>>
//        get() = _asteroidList

    private val _imageOfTheDay = MutableLiveData<ImageOfTheDayModel>()
    val imageOfTheDay: LiveData<ImageOfTheDayModel>
        get() = _imageOfTheDay

    private val _stateManagement = MutableLiveData<StateManagement>()

    val stateManagement: LiveData<StateManagement>
        get() = _stateManagement

    var asteroids: LiveData<List<Asteroid>>? = null
    fun getNeoFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            database.clear("2023-01-01")

            try {
//                _stateManagement.postValue(StateManagement.LOADING)
                val responseBody = RetrofitObject.retrofit.getNeoFeed(todayDate(), tomorrowDate()).string()

                val jsonObject = JSONObject(responseBody)
                val asteroidList = parseAsteroidsJsonResult(jsonObject)

//                _asteroidList.postValue(asteroidList)
//                _stateManagement.postValue(StateManagement.DONE)

                for (asteroid in asteroidList)
                    database.insert(asteroid)

                _stateManagement.postValue(StateManagement.LOADING)
                getNeoFeedFromDB()
                _stateManagement.postValue(StateManagement.DONE)

//                _stateManagement.postValue(StateManagement.DONE)

            } catch (e: Exception) {
//                _stateManagement.postValue(StateManagement.ERROR)
            }
        }
    }

    private fun getNeoFeedFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                asteroids = database.getAsteroids()
            } catch (e: Exception) {

            }
        }
    }

    fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                _imageOfTheDay.postValue(RetrofitObject.retrofit.getImageOfTheDay().body())
            } catch (e: Exception) {
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
    private fun tomorrowDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val tomorrowDate = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(tomorrowDate)
    }

    init {
        getNeoFeedFromDB()
        //    getNeoFeed()
        getImageOfTheDay()
    }
}