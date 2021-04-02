package com.example.coolieweather.buisness

import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.Result
import com.example.coolieweather.buisness.models.WeatherData

interface WeatherService {
    suspend fun getWeatherDetailsForCoordinates(geoPoint: GeoPoint): Result<WeatherData>
}