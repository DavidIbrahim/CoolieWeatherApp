package com.example.coolieweather.presentation.fragments.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.coolieweather.buisness.ImagesDataCacheService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class CameraViewModel  @Inject constructor(private val imagesDataCacheService: ImagesDataCacheService): ViewModel() {
}