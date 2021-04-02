package com.linguistic.linguistic.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coolieweather.framework.database.ImageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDao {

    @Query("SELECT * FROM images_saved  ORDER BY savedTime DESC Limit 1")
    suspend fun getLastImage(): ImageEntity?

    @Query("SELECT * FROM images_saved  ORDER BY savedTime DESC")
   suspend fun getAllImages(): List<ImageEntity>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(imageEntity: ImageEntity)

}