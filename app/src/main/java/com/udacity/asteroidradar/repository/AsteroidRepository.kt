package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    lateinit var asteroidList: List<Asteroid>
    lateinit var pictureOfDay: PictureOfDay

    //refresh catch
    suspend fun refreshData(){
        withContext(Dispatchers.IO) {
            val asteroids = AsteroidApi.retrofitService.getAsteroids(
                getToday(),
                getEndDay()).await()
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroids.string()))
            database.asteroidDatabaseDao.insertAll(*(asteroidList as ArrayList<Asteroid>).asDomainModel())
        }
    }

    //get picture
    suspend fun getPictureOfDay(){
        withContext(Dispatchers.IO) {
            val pictureOfDayValue = AsteroidApi.retrofitService
                .getPictureOfDay().await()
            pictureOfDay = pictureOfDayValue
        }
    }

    //delete all catch
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            database.asteroidDatabaseDao.deleteAllAsteroids()
        }
    }

    //delete Previous Day Asteroids catch
    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDatabaseDao.deletePreviousDayAsteroids(getToday())
        }
    }

    //get Week Asteroids
    suspend fun getWeekAsteroids() {
        withContext(Dispatchers.IO) {
            asteroidList = database.asteroidDatabaseDao.getWeekAsteroids(getToday(), getEndDay())
        }
    }

    //get Today Asteroids
    suspend fun getTodayAsteroids() {
        withContext(Dispatchers.IO) {
            asteroidList = database.asteroidDatabaseDao.getWeekAsteroids(getToday(), getToday())
        }
    }

    //get Saved Asteroids
    suspend fun getSavedAsteroids(){
        withContext(Dispatchers.IO) {
            asteroidList = database.asteroidDatabaseDao.getAllAsteroids()
        }
    }
}