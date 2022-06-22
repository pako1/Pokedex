package com.example.pokedex.mappers

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.example.pokedex.PokemonQuery
import com.example.pokedex.data.mappers.DetailQueryMappers
import com.example.pokedex.repository.data.FakeQueryDataProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class DetailQueryMappersTest {

    private lateinit var fakeApollo: ApolloClient
    private val detailTestQuery = PokemonQuery("bulbasaur")

    @OptIn(ApolloExperimental::class)
    @Before
    fun setup() {
        fakeApollo = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

    @Test
    fun test_mapping_from_query_to_domain() {
        val detailQueryMappers = DetailQueryMappers()
        val fakeQueryDataProvider = FakeQueryDataProvider()
        val pokemonDetail = detailQueryMappers.unwrapPokemonDetail(
            pokemonName = "bulbasaur",
            query = ApolloResponse.Builder(
                data = fakeQueryDataProvider.singleDetailPokemonData,
                operation = detailTestQuery,
                requestUuid = UUID.randomUUID()
            ).build()
        )
        Assert.assertEquals(fakeQueryDataProvider.pokemonDetail, pokemonDetail)
    }
}