package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.data.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPIService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): String // I used String as return type since I'll be using the scalar converter

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String
    ): Response<PictureOfDay> // I used Response since I wil be using Moshi as my converter
}