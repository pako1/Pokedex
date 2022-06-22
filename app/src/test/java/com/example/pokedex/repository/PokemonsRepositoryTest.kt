package com.example.pokedex.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.apollographql.apollo3.testing.runTest
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.datasource.remote.GraphQLSourceImpl
import com.example.pokedex.repository.data.FakeQueryDataProvider
import com.example.pokedex.data.mappers.PokemonQueryMappers
import com.example.pokedex.data.repo.PokemonsRepositoryImplementation
import com.example.pokedex.domain.usecases.ResponseToResultHelper
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

@OptIn(ApolloExperimental::class)
class PokemonsRepositoryTest {

    private lateinit var fakeApollo: ApolloClient
    private lateinit var graphQLSource: GraphQLSource
    private val mapper = PokemonQueryMappers()
    private val responseToResultHelper = ResponseToResultHelper()
    private val singlePokemonTestQuery = PokemonListQuery(1, 0)
    private val multiplePokemonTestQuery = PokemonListQuery(6, 0)
    private val fakeQueryDataProvider = FakeQueryDataProvider()

    @Before
    fun setup() {
        fakeApollo = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
        graphQLSource = GraphQLSourceImpl(fakeApollo)
    }

    @Test
    fun query_pokemons_single_result_no_error() = runTest {
        fakeApollo.enqueueTestResponse(
            singlePokemonTestQuery,
            fakeQueryDataProvider.singlePokemonListData
        )
        val pokemonsRepository = PokemonsRepositoryImplementation(graphQLSource, mapper)
        val response = pokemonsRepository.retrievePokemons(1, 0)
        val pokemonResult = responseToResultHelper.mapToResult(response)
        assertEquals(null, pokemonResult.error)
        assertEquals(fakeQueryDataProvider.singlePokemon, pokemonResult.data?.first())
    }

    @Test
    fun query_pokemons_multiple_result_no_error() = runTest {
        fakeApollo.enqueueTestResponse(
            multiplePokemonTestQuery,
            fakeQueryDataProvider.multiplePokemonListData
        )
        val pokemonsRepository = PokemonsRepositoryImplementation(graphQLSource, mapper)
        val response = pokemonsRepository.retrievePokemons(6, 0)
        val pokemonResult = responseToResultHelper.mapToResult(response)
        assertEquals(fakeQueryDataProvider.multiplePokemon, pokemonResult.data)
        assertEquals(null, pokemonResult.error)
    }

    @Test
    fun query_with_error() = runTest {
        fakeApollo.enqueueTestResponse(
            singlePokemonTestQuery,
            data = null,
            errors = listOf(
                Error(
                    message = "Random Error",
                    locations = null,
                    path = null,
                    extensions = null,
                    nonStandardFields = null
                )
            )
        )
        val pokemonsRepository = PokemonsRepositoryImplementation(graphQLSource, mapper)
        val response = pokemonsRepository.retrievePokemons(1, 0)
        val pokemonResult = responseToResultHelper.mapToResult(response)
        assertEquals("Random Error", pokemonResult.error)
        assertEquals(null, pokemonResult.data)
    }

}