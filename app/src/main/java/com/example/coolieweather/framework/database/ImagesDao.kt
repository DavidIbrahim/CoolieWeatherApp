package com.linguistic.linguistic.framework.datasource.cache.database

import androidx.room.*
import com.example.coolieweather.framework.database.ImageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDao {

    @Query("SELECT * FROM images_saved  ORDER BY savedTime DESC Limit 1")
     fun getLastImage(): Flow<ImageEntity>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(imageEntity: ImageEntity)

}