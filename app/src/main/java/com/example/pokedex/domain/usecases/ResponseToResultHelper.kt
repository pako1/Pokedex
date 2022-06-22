package com.example.pokedex.domain.usecases

import com.example.pokedex.data.repo.Response
import com.example.pokedex.domain.usecases.base.Result
import javax.inject.Inject

class ResponseToResultHelper @Inject constructor() {
    fun <T> mapToResult(response: Response<T>): Result<T?> {
        return when (response) {
            is Response.Success -> Result(data = response.data, error = null)
            is Response.Failure -> Result(data = null, error = response.error?.message)
            is Response.NetworkError -> Result(data = null, error = response.throwable?.message)
        }
    }
}

fun <T> T.asResult(): Result<T> = Result(data = this, error = null)
