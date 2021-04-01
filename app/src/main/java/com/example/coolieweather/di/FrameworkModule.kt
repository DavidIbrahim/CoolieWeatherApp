package com.example.coolieweather.di

import android.content.Context
import com.example.coolieweather.R
import com.kwabenaberko.openweathermaplib.constant.Languages
import com.kwabenaberko.openweathermaplib.constant.Units
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FrameworkModule {
    @Singleton
    @Provides
    fun provideOpenWeatherMapHelper(
        @ApplicationContext appContext: Context
    ): OpenWeatherMapHelper {
        //todo add encryption for API KEY for security
        val helper = OpenWeatherMapHelper(appContext.getString(R.string.api_key))
        //todo use Locale.getDefault().language when support multiple languages
        helper.setLanguage(Languages.ENGLISH)
        helper.setUnits(Units.METRIC)
        return helper
    }
}
