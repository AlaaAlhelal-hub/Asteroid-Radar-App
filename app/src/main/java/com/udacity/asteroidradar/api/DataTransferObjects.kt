package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidTable


@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)



@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,
    val codeName: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)


@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay(
    val id: Long,
    val mediaType: String,
    val title: String,
    val url: String
)

fun ArrayList<Asteroid>.asDomainModel(): Array<AsteroidTable> {
    return map {
        AsteroidTable(
            id = it.id,
            codeName = it.codeName,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
        .toTypedArray()
}
