package com.example.coolieweather.buisness.models


sealed class Result<out R> { companion object {
    }
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    fun fold(
        onSuccess: (value: R) -> Unit,
        onFailure: (error: Error) -> Unit = { }
    ) {
        when (this){
            is Success -> onSuccess(this.data)
            is Error -> onFailure(this)
        }
    }
    suspend fun suspendFold(
        onSuccess: suspend (value: R) -> Unit,
        onFailure: (error: Error) -> Unit = { }
    ) {
        when (this){
            is Success -> onSuccess(this.data)
            is Error -> onFailure(this)
        }
    }

    fun onSuccess(action: (Value:R)->Unit) {
        if(this is Success) action(this.data)
    }
    fun onError(onFailure: (error: Error) -> Unit = { }) {
        if(this is Error) onFailure(this)
    }

}