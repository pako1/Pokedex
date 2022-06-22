package com.example.pokedex.mappers

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.data.mappers.PokemonQueryMappers
import com.example.pokedex.repository.data.FakeQueryDataProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class PokemonsQueryMappersTest {

    private lateinit var fakeApollo: ApolloClient
    private val multiplePokemonTestQuery = PokemonListQuery(6, 0)

    @OptIn(ApolloExperimental::class)
    @Before
    fun setup() {
        fakeApollo = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

    @Test
    fun test_mapping_from_query_to_domain() {
        val pokemonQueryMappers = PokemonQueryMappers()
        val fakeQueryDataProvider = FakeQueryDataProvider()
        val pokemons = pokemonQueryMappers.unwrapPokemon(
            query = ApolloResponse.Builder(
                data = fakeQueryDataProvider.multiplePokemonListData,
                operation = multiplePokemonTestQuery,
                requestUuid = UUID.randomUUID()
            ).build()
        )
        assertEquals(fakeQueryDataProvider.multiplePokemon, pokemons)
    }
}