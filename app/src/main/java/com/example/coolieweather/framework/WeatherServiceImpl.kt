package com.example.coolieweather.framework

import com.example.coolieweather.buisness.models.WeatherData
import com.example.coolieweather.buisness.Result
import com.example.coolieweather.buisness.WeatherService
import com.example.coolieweather.buisness.models.GeoPoint
import com.example.coolieweather.framework.utils.FrameworkToDomainTransformer
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton

class WeatherServiceImpl @Inject constructor(
    private val helper: OpenWeatherMapHelper,
    private val frameworkToDomainTransformer: FrameworkToDomainTransformer
) : WeatherService {

    override suspend fun getWeatherDetailsForCoordinates(geoPoint: GeoPoint): Result<WeatherData> {
        return suspendCoroutine {
            helper.getCurrentWeatherByGeoCoordinates(
                geoPoint.latitude,
                geoPoint.longitude,
                object : CurrentWeatherCallback {
                    override fun onSuccess(currentWeather: CurrentWeather) {
                        val weatherData = frameworkToDomainTransformer.transform(currentWeather)
                        it.resume(Result.Success(weatherData))
                    }

                    override fun onFailure(throwable: Throwable) {
                        it.resume(Result.Error(throwable))
                    }
                })

        }

    }
}