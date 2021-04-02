package com.example.coolieweather.presentation.fragments.gallery

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.coolieweather.buisness.ImagesDataCacheService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel  @Inject constructor(cacheService: ImagesDataCacheService): ViewModel() {
    val imagesUri: LiveData<List<Uri>> = liveData {
      emit(cacheService.getAllImages())
    }
}