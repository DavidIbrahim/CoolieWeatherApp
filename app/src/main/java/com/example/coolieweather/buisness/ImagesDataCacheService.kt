package com.example.coolieweather.buisness

import android.net.Uri

interface ImagesDataCacheService {

    suspend fun saveImage(imageUri: Uri)
}
