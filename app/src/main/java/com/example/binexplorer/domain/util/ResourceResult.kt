package com.example.binexplorer.domain.util

sealed class ResourceResult<out T> {
    object Loading : ResourceResult<Nothing>()
    data class Success<out T>(val data: T) : ResourceResult<T>()
    data class Error(val exception: Exception) : ResourceResult<Nothing>()
}

inline fun <T, R> ResourceResult<T>.map(transform: (T) -> R): ResourceResult<R> {
    return when (this) {
        is ResourceResult.Success -> ResourceResult.Success(transform(data))
        is ResourceResult.Error -> this
        ResourceResult.Loading -> ResourceResult.Loading
    }
}