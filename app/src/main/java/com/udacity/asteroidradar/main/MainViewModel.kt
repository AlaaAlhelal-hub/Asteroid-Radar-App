package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.getEndDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var database = AsteroidDatabase.getInstance(application)
    private var repository = AsteroidRepository(database)


    private var _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    private var _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid>()
    val navigateToDetailAsteroid: LiveData<Asteroid>
        get() = _navigateToDetailAsteroid


    init {
        viewModelScope.launch {
            try {
                repository.refreshData()
                repository.getPictureOfDay()
                _asteroidList.value = repository.asteroidList
                _pictureOfDay.value = repository.pictureOfDay
            } catch (e: Exception) {
                Log.i("Error", "refreshing data from api: ${e.message}")
            }
        }
    }


    fun onNavigationDone() {
        _navigateToDetailAsteroid.value = null
    }

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToDetailAsteroid.value = asteroid
    }

    fun getWeekAsteroids(){
        viewModelScope.launch {
            try {
                repository.getWeekAsteroids()
                _asteroidList.value = repository.asteroidList
            } catch (e: Exception) {
                Log.i("Error", "retrieving data Week data: ${e.message}")
            }
        }
    }

    fun getTodayAsteroids(){
        viewModelScope.launch {
            try {
                repository.getTodayAsteroids()
                _asteroidList.value = repository.asteroidList
            } catch (e: Exception) {
                Log.i("Error", "retrieving Today data ${e.message}")
            }
        }
    }

    fun getSavedAsteroids(){
        viewModelScope.launch {
            try {
                repository.getSavedAsteroids()
                _asteroidList.value = repository.asteroidList
            } catch (e: Exception) {
                Log.i("Error", "retrieving Saved data ${e.message}")
            }
        }
    }


}