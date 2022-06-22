package com.example.pokedex.usecases

import com.apollographql.apollo3.api.Error
import com.example.pokedex.data.repo.Response
import com.example.pokedex.domain.usecases.ResponseToResultHelper
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.repository.data.FakeQueryDataProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class ResponseToResultHelperTest {

    @Test
    fun map_to_success() {
        val responseToResultHelper = ResponseToResultHelper()
        val fakeQueryDataProvider = FakeQueryDataProvider()
        val successResult =
            responseToResultHelper.mapToResult(Response.Success(data = fakeQueryDataProvider.pokemonDetail))
        val result = Result(data = fakeQueryDataProvider.pokemonDetail, error = null)
        assertEquals(result, successResult)
    }

    @Test
    fun map_to_failure() {
        val responseToResultHelper = ResponseToResultHelper()
        val failureResult =
            responseToResultHelper.mapToResult(
                Response.Failure(
                    error = Error(
                        message = "",
                        locations = null,
                        path = null,
                        extensions = null,
                        nonStandardFields = null
                    )
                )
            )
        val result = Result(data = null, error = "")
        assertEquals(result, failureResult)
    }

    @Test
    fun map_to_network_error() {
        val responseToResultHelper = ResponseToResultHelper()
        val networkErrorResult =
            responseToResultHelper.mapToResult(
                Response.NetworkError(Throwable("cause"))
            )
        val result = Result(data = null, error = "cause")
        assertEquals(result, networkErrorResult)
    }
}