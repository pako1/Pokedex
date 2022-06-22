package com.example.pokedex.domain.repositories

import com.example.pokedex.data.repo.Response
import com.example.pokedex.domain.models.PokemonDetail

interface DetailRepository {
    /**
     * Fetches pokemon details by providing the pokemon name as a parameter wrapped inside a response.
     *
     * [pokemonName] represents the name of the Pokemon and must be a string type
     */
    suspend fun fetchPokemonDetails(pokemonName: String): Response<PokemonDetail>
}