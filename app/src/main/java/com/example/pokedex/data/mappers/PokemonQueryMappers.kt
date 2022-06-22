package com.example.pokedex.data.mappers

import com.apollographql.apollo3.api.ApolloResponse
import com.example.pokedex.PokemonListQuery
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.addHash
import javax.inject.Inject

class PokemonQueryMappers @Inject constructor() {

    fun unwrapPokemon(query: ApolloResponse<PokemonListQuery.Data>): List<Pokemon> {
        val pokemons = mutableListOf<Pokemon>()
        query.data?.pokemons?.results?.let { result ->
            result.map {
                pokemons.add(
                    Pokemon(
                        id = (it?.id ?: 0).addHash(),
                        name = it?.name ?: "",
                        artwork = it?.artwork ?: "",
                        isFavorite = false
                    )
                )
            }
        }
        return pokemons
    }
}