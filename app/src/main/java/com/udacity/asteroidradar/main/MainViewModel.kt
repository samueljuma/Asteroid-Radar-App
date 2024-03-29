package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = AsteroidRepository()

    private val _listOfAsteroids = MutableLiveData<List<Asteroid>?>()
    val listOfAsteroids: LiveData<List<Asteroid>?> =_listOfAsteroids

    fun fetchAsteroids(startDate: String, endDate: String, apiKey: String){
        viewModelScope.launch (Dispatchers.IO) {
            val asteroidList = repository.fetchAsteroids(startDate, endDate, apiKey)
            _listOfAsteroids.postValue(asteroidList)
        }
    }

    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?> =_navigateToDetails

    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToDetails.value = asteroid
    }

    fun doneNavigatingToDetails(){
        _navigateToDetails.value =null
    }


}