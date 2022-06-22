package com.example.pokedex.presentation.details

sealed class DetailState<out T> {
    object Loading : DetailState<Nothing>()
    object Failure : DetailState<Nothing>()
    data class Success<T>(val data: T) : DetailState<T>()
}