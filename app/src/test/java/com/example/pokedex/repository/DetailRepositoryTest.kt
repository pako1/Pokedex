package com.example.pokedex.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.apollographql.apollo3.testing.runTest
import com.example.pokedex.PokemonQuery
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.datasource.remote.GraphQLSourceImpl
import com.example.pokedex.data.mappers.DetailQueryMappers
import com.example.pokedex.data.repo.DetailRepositoryImplementation
import com.example.pokedex.domain.usecases.ResponseToResultHelper
import com.example.pokedex.repository.data.FakeQueryDataProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ApolloExperimental::class)
class DetailRepositoryTest {
    private lateinit var graphQLSource: GraphQLSource
    private val detailQueryMappers = DetailQueryMappers()
    private lateinit var fakeApollo: ApolloClient
    private val detailTestQuery = PokemonQuery("bulbasaur")
    private val fakeQueryDataProvider = FakeQueryDataProvider()
    private val responseToResultHelper = ResponseToResultHelper()

    @Before
    fun setup() {
        fakeApollo = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
        graphQLSource = GraphQLSourceImpl(fakeApollo)
    }

    @Test
    fun query_pokemon_detail_result_no_error() = runTest {
        fakeApollo.enqueueTestResponse(
            detailTestQuery,
            fakeQueryDataProvider.singleDetailPokemonData
        )
        val repository = DetailRepositoryImplementation(detailQueryMappers, graphQLSource)
        val response = repository.fetchPokemonDetails("bulbasaur")
        val detailResult = responseToResultHelper.mapToResult(response)
        assertEquals(fakeQueryDataProvider.pokemonDetail, detailResult.data)
        assertEquals(null, detailResult.error)
    }

    @Test
    fun query_pokemon_detail_result_with_error() = runTest {
        fakeApollo.enqueueTestResponse(
            detailTestQuery,
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
        val repository = DetailRepositoryImplementation(detailQueryMappers, graphQLSource)
        val response = repository.fetchPokemonDetails("bulbasaur")
        val detailResult = responseToResultHelper.mapToResult(response)
        assertEquals("Random Error", detailResult.error)
        assertEquals(null, detailResult.data)
    }

}