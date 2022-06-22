package com.example.pokedex.data.datasource.local

import com.example.pokedex.data.datasource.local.database.Pokebase
import com.example.pokedex.data.model.PokemonEntity
import javax.inject.Inject

class RoomSourceImpl @Inject constructor(
    private val pokeDatabase: Pokebase,
) : RoomSource {
    override fun getFavoritePokemons() = pokeDatabase.pokeDao().getFavoritePokemons()

    override fun getFavoritePokemonById(id: Int) =
        pokeDatabase.pokeDao().loadFavoritePokemonById(id)

    override fun makeFavoritePokemon(vararg pokemonEntity: PokemonEntity) =
        pokeDatabase.pokeDao().insertFavoritePokemons(*pokemonEntity)

    override fun undoFavoritePokemon(pokemonEntity: PokemonEntity) =
        pokeDatabase.pokeDao().deletePokemon(pokemonEntity)

    override fun doesPokemonExistInFavorites(pokemonEntity: PokemonEntity): Boolean {
        return pokeDatabase.pokeDao().doesPokemonExistAlready(pokemonEntity.id)
    }


}