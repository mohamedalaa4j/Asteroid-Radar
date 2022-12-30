package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.AsteroidModel
import com.udacity.asteroidradar.ImageOfTheDayModel
import com.udacity.asteroidradar.api.RetrofitObject
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<AsteroidModel>()
    val response: LiveData<AsteroidModel>
        get() = _response

    private val _imageOfTheDay = MutableLiveData<ImageOfTheDayModel>()
    val imageOfTheDay: LiveData<ImageOfTheDayModel>
        get() = _imageOfTheDay

    fun getNeoFeed() {
        viewModelScope.launch {
            try {
                _response.postValue(RetrofitObject.retrofit.getNeoFeed("2015-09-07", "2015-09-08").body())
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
//    fun getDate():String{
//        val date = Calendar.getInstance().time
//       // val formatter = SimpleDateFormat.getDateTimeInstance()
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        val formatedDate = sdf.format(date)
//        Log.e("formatedDate",formatedDate)
//        return formatedDate
//    }

}