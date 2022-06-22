package com.example.pokedex.data.datasource.remote

import com.apollographql.apollo3.ApolloClient
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.PokemonQuery
import javax.inject.Inject


class GraphQLSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GraphQLSource {

    override suspend fun retrieveAllPokemon(limit: Int?, offset: Int?) = apolloClient
        .query(PokemonListQuery(limit = limit, offset = offset))
        .execute()

    override suspend fun retrievePokemonDetails(pokemonName: String) = apolloClient
        .query(PokemonQuery(pokemonName))
        .execute()

}