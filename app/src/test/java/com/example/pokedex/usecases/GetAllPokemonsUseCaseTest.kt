package com.example.pokedex.usecases

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.testing.runTest
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.mappers.PokemonQueryMappers
import com.example.pokedex.data.repo.PokemonsRepositoryImplementation
import com.example.pokedex.domain.usecases.AllPokemonsParams
import com.example.pokedex.domain.usecases.GetAllPokemonsUseCase
import com.example.pokedex.domain.usecases.ResponseToResultHelper
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.repository.data.FakeQueryDataProvider
import io.mockk.coEvery
import io.mockk.coVerifyAll
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.util.*

class GetAllPokemonsUseCaseTest {

    @Test
    fun get_all_pokemons() = runTest {
        val fakeQueryDataProvider = FakeQueryDataProvider()
        val multiplePokemonTestQuery = PokemonListQuery(6, 0)
        val graphQLSource = mockk<GraphQLSource>()
        coEvery {
            graphQLSource.retrieveAllPokemon(
                eq(6),
                eq(0)
            )
        } returns ApolloResponse.Builder(
            data = fakeQueryDataProvider.multiplePokemonListData,
            operation = multiplePokemonTestQuery,
            requestUuid = UUID.randomUUID()
        ).build()
        val pokemonsQueryMappers = PokemonQueryMappers()
        val pokemonsRepository =
            PokemonsRepositoryImplementation(graphQLSource, pokemonsQueryMappers)
        val responseToResultHelper = ResponseToResultHelper()

        val getAllPokemonsUseCase = GetAllPokemonsUseCase(
            pokemonsRepository,
            responseToResultHelper
        )
        val pokemonsResult = getAllPokemonsUseCase.invoke(AllPokemonsParams(6, 0))
        Assert.assertEquals(
            Result(data = fakeQueryDataProvider.multiplePokemon, error = null),
            pokemonsResult
        )
        coVerifyAll {
            graphQLSource.retrieveAllPokemon(6, 0)
        }
    }
}