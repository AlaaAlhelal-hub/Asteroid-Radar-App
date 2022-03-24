package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroid")
data class AsteroidTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codeName: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)



fun List<AsteroidTable>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id= it.id,
            codeName= it.codeName,
            closeApproachDate= it.closeApproachDate,
            absoluteMagnitude= it.absoluteMagnitude,
            estimatedDiameter= it.estimatedDiameter,
            relativeVelocity= it.relativeVelocity,
            distanceFromEarth= it.distanceFromEarth,
            isPotentiallyHazardous= it.isPotentiallyHazardous)
    }
}
