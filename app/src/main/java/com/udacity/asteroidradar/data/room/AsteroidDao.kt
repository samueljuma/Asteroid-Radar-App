package com.udacity.asteroidradar.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.Asteroid
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface AsteroidDao {

    //query sorts result by date
    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate")
    fun getALlAsteroids() : LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN DATE(:startDate) AND DATE(:endDate) ORDER BY closeApproachDate")
    fun getWeekAsteroids(startDate: String, endDate: String) : LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate == DATE(:todayDate)")
    fun getTodayAsteroids(todayDate: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroidsToDb(asteroids: List<Asteroid>)

}