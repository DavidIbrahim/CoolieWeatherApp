package com.example.coolieweather.presentation.fragments.weatherscreen

import com.example.coolieweather.buisness.models.WeatherData
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.WeatherService
import com.example.coolieweather.buisness.models.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class WeatherViewModel @Inject constructor(private val weatherService: WeatherService) :
    ViewModel() {
    val grantedLocationPermission: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _weatherData: MutableLiveData<Result<WeatherData>> = MutableLiveData(null)
    val weatherData = _weatherData
    fun getWeatherDetails(geoPoint: GeoPoint) {
        viewModelScope.launch {
            val weatherResult =
                weatherService.getWeatherDetailsForCoordinates(geoPoint)
            _weatherData.postValue(weatherResult)
        }

    }


    fun setUserLocation(location: Location?) {
        if (location != null) {

        }
    }
}