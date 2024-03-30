package com.udacity.asteroidradar.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.data.room.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.utils.getCurrentDate
import com.udacity.asteroidradar.utils.getEndDate
import retrofit2.HttpException

class FetchNewAsteroidsDataWorker(
    appContext: Context, params: WorkerParameters
): CoroutineWorker(appContext, params){
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.fetchAsteroids(getCurrentDate(), getEndDate(getCurrentDate()), BuildConfig.API_KEY)
            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }
}