package com.linguistic.linguistic.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coolieweather.framework.database.ImageEntity

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImagesDataBase : RoomDatabase() {
    abstract fun textMessagesDao(): ImagesDao
}