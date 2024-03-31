package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.data.api.RetrofitClient
import com.udacity.asteroidradar.data.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.room.AsteroidDao
import com.udacity.asteroidradar.data.room.AsteroidDatabase
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.reflect.Executable
import java.security.interfaces.RSAKey

class AsteroidRepository (private val database: AsteroidDatabase) {

    //Function fetches Picture of the Day from the Internet
     suspend fun fetchPictureOfDayAndAndSaveToDb() {
        lateinit var picture: PictureOfDay

        try{
            val response = RetrofitClient.nasaAPIService.getPictureOfDay(BuildConfig.API_KEY)
            val body = response.body()
            if(body != null){
                picture = body

                //Save photo to Room Database
                database.pictureOfDayDao.insertPhotoOFDayToDb(picture)
            }
        }catch (exception: HttpException){
            Log.e("Tagy", "Failed to get photo from API", exception)
        }

    }

    suspend fun fetchAsteroids (startDate: String, endDate: String, apiKey: String) {
         try {
            val response = RetrofitClient.nasaAPIService.getAsteroids(startDate, endDate, apiKey)

             //insert data into room database
             val asteroids = parseAsteroidsJsonResult(JSONObject(response))
             database.asteroidDao.insertAsteroidsToDb(asteroids)

        }catch (e: IOException){
             Log.e("TAGGY","You got an IOException error", e)

        }catch (e: HttpException){
            Log.e("TAGGY","You got a network error", e)
        }
    }

    // get list of Asteroids from room database
    fun getAsteroidsFromDb(): LiveData<List<Asteroid>> = database.asteroidDao.getALlAsteroids()

    fun getPictureOfDayFromDB(): LiveData<PictureOfDay> = database.pictureOfDayDao.getPictureOfDay()

}