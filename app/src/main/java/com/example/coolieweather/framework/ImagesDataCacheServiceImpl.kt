package com.example.coolieweather.framework

import android.net.Uri
import com.example.coolieweather.buisness.ImagesDataCacheService
import com.example.coolieweather.framework.database.ImageEntity
import com.linguistic.linguistic.framework.datasource.cache.database.ImagesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataCacheServiceImpl @Inject constructor(private val imagesDao: ImagesDao):
    ImagesDataCacheService {
    override suspend fun saveImage(imageUri: Uri){
        imagesDao.insert(ImageEntity(imagePath = imageUri.path!!,savedTime = Instant.now().toEpochMilli()))
    }

    override suspend fun getAllImages(): List<Uri> {
        return imagesDao.getAllImages().map { Uri.parse(it.imagePath)
        }
    }
}