package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    val listOfAsteroids = listOf(
        Asteroid(0,"68347 (2001 B67)","2020-02-08",20.0,
            20.0,20.0,20.0,true),
        Asteroid(0,"68347 (2001 B67)","2020-02-08",20.0,
            20.0,20.0,20.0,true),
        Asteroid(0,"68347 (2001 B67)","2020-02-08",20.0,
            20.0,20.0,20.0,true),
        Asteroid(0,"68347 (2001 B67)","2020-02-08",20.0,
            20.0,20.0,20.0,true),
        Asteroid(0,"68347 (2001 B67)","2020-02-08",20.0,
            20.0,20.0,20.0,true),
    )

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?> =_navigateToDetails

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToDetails.value = asteroid
    }

    fun doneNavigatingToDetails(){
        _navigateToDetails.value =null
    }


}