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

    @Query("SELECT * FROM asteroids")
    fun getALlAsteroids() : LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroidsToDb(asteroids: List<Asteroid>)
}