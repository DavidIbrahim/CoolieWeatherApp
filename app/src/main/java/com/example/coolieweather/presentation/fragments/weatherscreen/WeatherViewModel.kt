package com.example.coolieweather.presentation.fragments.weatherscreen

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolieweather.buisness.ImagesDataCacheService
import com.example.coolieweather.buisness.WeatherService
import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel

class WeatherViewModel @Inject constructor(
    private val weatherService: WeatherService,
    private val imagesDataCacheService: ImagesDataCacheService
) :
    ViewModel() {

    val grantedLocationPermission: MutableLiveData<Boolean> = MutableLiveData(false)


    private val _currentBackgroundImageUri: MutableLiveData<Uri> = MutableLiveData(null)
    val currentBackgroundImageUri: LiveData<Uri> = _currentBackgroundImageUri

    //this is the background with weather data on it
    var currentWeatherImageUri: Uri? = null

    private val _weatherData: MutableLiveData<Result<WeatherData>> = MutableLiveData(null)
    val weatherData: MutableLiveData<Result<WeatherData>> = _weatherData


    private val _currentLocation: MutableLiveData<GeoPoint> = MutableLiveData(null)
    val currentLocation: MutableLiveData<GeoPoint> = _currentLocation

    fun updateWeatherDetails(geoPoint: GeoPoint) {
        _currentLocation.postValue(geoPoint)
        fetchWeatherData()

    }

     fun fetchWeatherData() {
        viewModelScope.launch {
            delay(100)
            val weatherResult =
                currentLocation.value?.let { weatherService.getWeatherDetailsForCoordinates(it) }
            _weatherData.postValue(weatherResult)
        }
    }

    fun saveWeatherImageInDatabase(uri: Uri) {
        currentWeatherImageUri = uri
        viewModelScope.launch {
            imagesDataCacheService.saveImage(uri)
        }
    }

    fun setCurrentBackground(uri: Uri?) {
        Timber.d("current  background image $uri")
        _currentBackgroundImageUri.postValue(uri)
    }


}