package com.example.pokedex.data.datasource.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.PokemonQuery

interface GraphQLSource {
    suspend fun retrieveAllPokemon(limit: Int?, offset: Int?): ApolloResponse<PokemonListQuery.Data>
    suspend fun retrievePokemonDetails(pokemonName: String): ApolloResponse<PokemonQuery.Data>
}