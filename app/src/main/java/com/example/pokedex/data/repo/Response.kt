package com.example.pokedex.data.repo

import com.apollographql.apollo3.api.Error

sealed class Response<out T> {
    data class Success<T>(val data: T?) : Response<T>()
    data class Failure(val error: Error?) : Response<Nothing>()
    data class NetworkError(val throwable: Throwable?) : Response<Nothing>()
}