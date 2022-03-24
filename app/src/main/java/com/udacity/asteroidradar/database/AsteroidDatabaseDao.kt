package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidTable: AsteroidTable)

    // get saved asteroids
    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): List<Asteroid>

    // get week\today asteroids
    @Query("SELECT * FROM asteroid WHERE  closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(startDate: String, endDate: String): List<Asteroid>

    @Query("DELETE FROM asteroid")
    fun deleteAllAsteroids()

    @Query("DELETE FROM asteroid WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int
}