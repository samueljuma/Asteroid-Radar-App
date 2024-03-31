package com.udacity.asteroidradar.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay

@Dao
interface PictureOfDayDao {
    @Query("SELECT * FROM picture_of_day")
    fun getPictureOfDay() : LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoOFDayToDb(pictureOfDay: PictureOfDay)
}