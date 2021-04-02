package com.example.coolieweather.presentation.fragments.weatherscreen

import android.location.Location
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolieweather.buisness.ImagesDataCacheService
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.WeatherService
import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val _weatherData: MutableLiveData<Result<WeatherData>> = MutableLiveData(null)
    val weatherData: MutableLiveData<Result<WeatherData>> = _weatherData
    fun updateWeatherDetails(geoPoint: GeoPoint) {
        viewModelScope.launch {
            val weatherResult =
                weatherService.getWeatherDetailsForCoordinates(geoPoint)
            _weatherData.postValue(weatherResult)
        }

    }

    fun setCurrentBackground(uri: Uri) {
        _currentBackgroundImageUri.postValue(uri)
    }


}