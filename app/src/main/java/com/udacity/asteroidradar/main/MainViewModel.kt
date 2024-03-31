package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.data.room.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (
    application: Application // Will be needed for creating database
) : ViewModel() {

    //initialize database and repository variables
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    //Fetch asteroids from the network
    fun fetchAsteroids(startDate: String, endDate: String, apiKey: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.fetchAsteroids(startDate, endDate, apiKey)
        }
    }

    //Get list of Asteroids from room database
    val asteroids = repository.getAsteroidsFromDb()

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?> =_navigateToDetails

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToDetails.value = asteroid
    }

    fun doneNavigatingToDetails(){
        _navigateToDetails.value =null
    }


    //The vieModelFactory helps to create a viewModel with arguments given
    // This implementation made me appreciate the role of dependency injection. It took me a while to
    // figure out if I needed a factory (Spend more than half a day on this). I guess that's because I already knew a library like Hilt
    // that could have made my work way easier. Felt like I was learning backwards but it was a good experience.
    class Factory( val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED CAST")
                return MainViewModel(app) as T
            }
            throw IllegalAccessException("Unable to construct ViewModel")
        }
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> = _pictureOfDay
    fun fetchPictureOfDay(){
        viewModelScope.launch {
            _pictureOfDay.value = repository.getPictureOfDay()
        }
    }


}