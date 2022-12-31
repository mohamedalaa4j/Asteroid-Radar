package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids_table")
data class AsteroidEntity(

    @PrimaryKey(autoGenerate = true)
    var AsteroidId: Long,

    @ColumnInfo(name = "codename")
    val codename: String,

    @ColumnInfo(name = "closeApproachDate")
    val closeApproachDate: String,

    @ColumnInfo(name = "absoluteMagnitude")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "estimatedDiameter")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relativeVelocity")
    val relativeVelocity: Double,

    @ColumnInfo(name = "distanceFromEarth")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean
)

