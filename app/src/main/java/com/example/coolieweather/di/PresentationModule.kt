package com.example.coolieweather.di

import com.example.coolieweather.buisness.ImagesDataCacheService
import com.example.coolieweather.buisness.WeatherService
import com.example.coolieweather.framework.ImagesDataCacheServiceImpl
import com.example.coolieweather.framework.WeatherServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class PresentationModule {
    @Singleton
    @Binds
    abstract fun provideLanguagesServices(
        impl: WeatherServiceImpl
    ): WeatherService
    @Singleton
    @Binds
    abstract fun provideImagesCacheService(
        impl: ImagesDataCacheServiceImpl
    ): ImagesDataCacheService

}