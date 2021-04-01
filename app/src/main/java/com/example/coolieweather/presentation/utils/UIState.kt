package com.example.coolieweather.presentation.utils

import java.lang.Exception

sealed class UIState<out R>()
data class Success<out T>(val data: T) : UIState<T>()
data class Error(val throwable: Throwable) : UIState<Nothing>()
object Loading : UIState<Nothing>()
