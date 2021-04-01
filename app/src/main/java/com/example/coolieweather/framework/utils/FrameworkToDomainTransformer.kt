package com.example.coolieweather.framework.utils

import com.example.coolieweather.buisness.models.WeatherData
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrameworkToDomainTransformer @Inject constructor() {
    fun transform(currentWeather: CurrentWeather): WeatherData {
      return  WeatherData(currentWeather.weather[0].description, currentWeather.main.tempMax)

        /*
          Log.v(TAG, "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                  +"Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                  +"Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                  +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                  +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
          );*/
    }
}
