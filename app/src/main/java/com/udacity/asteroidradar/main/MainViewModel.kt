package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.data.room.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.utils.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import androidx.lifecycle.Transformations

class MainViewModel (
    application: Application // Will be needed for creating database
) : ViewModel() {

    //initialize database and repository variables
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    //Fetch asteroids from the network and save in DD
    fun fetchAsteroids(startDate: String, endDate: String, apiKey: String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.fetchAsteroids(startDate, endDate, apiKey)
        }
    }

    //FetchPictureOfDay from network and Save in DB
    fun fetchPictureOfDay(){
        viewModelScope.launch (Dispatchers.IO) {
            repository.fetchPictureOfDayAndAndSaveToDb()
        }
    }

    /**
     *  Get list of Asteroids from room database based on filter
     *  MediatorLiveData in use
     */

    private val _filter = MutableLiveData<Filter>(Filter.ALL)
    val asteroids: LiveData<List<Asteroid>> = MediatorLiveData<List<Asteroid>>().apply {
        addSource(_filter) { filter ->
            viewModelScope.launch {
                val filteredAsteroids = repository.getAsteroidsByFilter(filter)
                addSource(filteredAsteroids) { asteroids ->
                    value = asteroids
                }
            }
        }
    }

    fun setFilter(filter: Filter){
        _filter.value = filter
    }


    //Get PhotoOfDay from room database
    val pictureOfDay: LiveData<PictureOfDay> = repository.getPictureOfDayFromDB()

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



}