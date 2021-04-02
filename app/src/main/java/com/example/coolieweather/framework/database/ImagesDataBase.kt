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

    /*companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: OfflineDataBase? = null

        fun getDatabase(context: Context): OfflineDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OfflineDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }*/
}