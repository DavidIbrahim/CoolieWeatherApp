package com.example.coolieweather.buisness.models


sealed class Result<out R> { companion object {
    }
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}