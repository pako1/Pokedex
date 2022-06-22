package com.example.pokedex.usecases

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.testing.runTest
import com.example.pokedex.PokemonQuery
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.mappers.DetailQueryMappers
import com.example.pokedex.data.repo.DetailRepositoryImplementation
import com.example.pokedex.domain.usecases.*
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.repository.data.FakeQueryDataProvider
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.util.*

class GetDetailUseCaseTest {

    @Test
    fun get_details() = runTest {
        val fakeQueryDataProvider = FakeQueryDataProvider()
        val pokemonName = "bulbasaur"
        val detailTestQuery = PokemonQuery(pokemonName)
        val graphQLSource = mockk<GraphQLSource>()
        coEvery {
            graphQLSource.retrievePokemonDetails(pokemonName)
        } returns ApolloResponse.Builder(
            data = fakeQueryDataProvider.singleDetailPokemonData,
            operation = detailTestQuery,
            requestUuid = UUID.randomUUID()
        ).build()
        val detailQueryMappers = DetailQueryMappers()
        val detailsRepository =
            DetailRepositoryImplementation(detailQueryMappers, graphQLSource)
        val responseToResultHelper = ResponseToResultHelper()

        val getPokemonDetailUseCase = GetPokemonDetailUseCase(
            detailsRepository,
            responseToResultHelper
        )
        val pokemonDetailsResult = getPokemonDetailUseCase.invoke(PokemonDetailParams(pokemonName))
        Assert.assertEquals(
            Result(data = fakeQueryDataProvider.pokemonDetail, error = null),
            pokemonDetailsResult
        )
        coVerifyAll {
            graphQLSource.retrievePokemonDetails(pokemonName)
        }
    }
}