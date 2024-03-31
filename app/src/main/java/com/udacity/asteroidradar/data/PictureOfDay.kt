package com.udacity.asteroidradar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "picture_of_day")
@JsonClass(generateAdapter = true)
data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    @PrimaryKey val url: String
)