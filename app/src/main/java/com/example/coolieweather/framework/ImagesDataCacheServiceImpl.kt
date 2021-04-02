package com.example.coolieweather.framework

import android.net.Uri
import com.example.coolieweather.buisness.ImagesDataCacheService
import com.example.coolieweather.framework.database.ImageEntity
import com.linguistic.linguistic.framework.datasource.cache.database.ImagesDao
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataCacheServiceImpl @Inject constructor(private val imagesDao: ImagesDao):
    ImagesDataCacheService {
    override suspend fun saveImage(imageUri: Uri){
        imagesDao.insert(ImageEntity(imagePath = imageUri.path!!,savedTime = Instant.now().toEpochMilli()))
    }

}