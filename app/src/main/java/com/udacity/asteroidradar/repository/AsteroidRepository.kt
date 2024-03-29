package com.udacity.asteroidradar.repository

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.RetrofitClient
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class AsteroidRepository {

    suspend fun fetchAsteroids (startDate: String, endDate: String, apiKey: String): ArrayList<Asteroid>? {
         try {
            val response = RetrofitClient.nasaAPIService.getAsteroids(startDate, endDate, apiKey)
           return parseAsteroidsJsonResult(JSONObject(response))
        }catch (e: IOException){
           return null
        }catch (e: HttpException){
            Log.e("TAGGY","You got a network error", e)
           return null
        }
    }
}