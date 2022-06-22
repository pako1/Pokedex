package com.example.pokedex.domain.repositories

import com.example.pokedex.data.repo.Response
import com.example.pokedex.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonsRepository {
    /**
     * Fetches pokemon details by providing the pokemon name as a parameter wrapped inside a response.
     *
     * [limit] represents how many pokemons we will retrieve and must be an int type or can be skipped
     *
     * [offset] represents how many pokemons will be skipped and must be an int type or can be skipped
     */
    suspend fun retrievePokemons(limit: Int?, offset: Int?): Response<List<Pokemon>>

    suspend fun getAllFavoritePokemons(): Flow<List<Pokemon>>

    suspend fun updateFavoritePokemon(pokemon: Pokemon)
}