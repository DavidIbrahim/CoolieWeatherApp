package com.example.coolieweather.buisness.models

data class WeatherData(
    val weatherDescription: String,
    val temp: Double,
    val windSpeed: Double,
    val humidity: Double,
    val geoPoint: GeoPoint
)