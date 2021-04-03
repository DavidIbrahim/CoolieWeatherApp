package com.example.coolieweather.di

import android.content.Context
import androidx.room.Room
import com.example.coolieweather.R
import com.kwabenaberko.openweathermaplib.constant.Languages
import com.kwabenaberko.openweathermaplib.constant.Units
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.linguistic.linguistic.framework.datasource.cache.database.ImagesDataBase
import com.linguistic.linguistic.framework.datasource.cache.database.ImagesDao
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

        val helper = OpenWeatherMapHelper(appContext.getString(R.string.api_key))
        //todo use Locale.getDefault().language when support multiple languages
        helper.setLanguage(Languages.ENGLISH)
        helper.setUnits(Units.METRIC)
        return helper
    }


    @Singleton
    @Provides
    fun provideIMagesDataBase(
        @ApplicationContext app: Context
    ): ImagesDataBase {
        return Room.databaseBuilder(app, ImagesDataBase::class.java, "weather_images")
            .build()
    }

    @Singleton
    @Provides
    fun provideTextMessagesDao(db: ImagesDataBase): ImagesDao {
        return db.textMessagesDao();
    }

}
