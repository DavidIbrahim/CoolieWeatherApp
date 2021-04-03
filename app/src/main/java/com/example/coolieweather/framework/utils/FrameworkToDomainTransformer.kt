package com.example.coolieweather.framework.utils

import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.buisness.models.WeatherData
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrameworkToDomainTransformer @Inject constructor() {
    fun transform(currentWeather: CurrentWeather): WeatherData {
      return  WeatherData(currentWeather.weather[0].description, currentWeather.main.tempMax ,currentWeather.wind.speed,
      currentWeather.main.humidity,
          GeoPoint(currentWeather.coord.lat,currentWeather.coord.lon)
      )
    }
}
