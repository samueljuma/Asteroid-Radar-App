package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val nasaAPIService: NasaAPIService by lazy {
        retrofit.create(NasaAPIService::class.java)
    }
}