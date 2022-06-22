package com.example.pokedex.data.datasource.local

import com.example.pokedex.data.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface RoomSource {

    fun getFavoritePokemons(): Flow<List<PokemonEntity>>

    fun getFavoritePokemonById(id: Int): PokemonEntity

    fun makeFavoritePokemon(vararg pokemonEntity: PokemonEntity)

    fun undoFavoritePokemon(pokemonEntity: PokemonEntity)

    fun doesPokemonExistInFavorites(pokemonEntity: PokemonEntity) : Boolean
}