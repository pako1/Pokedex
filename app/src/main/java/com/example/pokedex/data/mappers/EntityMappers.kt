package com.example.pokedex.data.mappers

import com.example.pokedex.data.model.PokemonEntity
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.addHash
import com.example.pokedex.utils.removeHash
import javax.inject.Inject

class EntityMappers @Inject constructor() {

    fun toEntity(pokemon: Pokemon): PokemonEntity = PokemonEntity(
        name = pokemon.name,
        id = pokemon.id.removeHash(),
        artwork = pokemon.artwork
    )

    fun toModel(pokemonEntity: PokemonEntity): Pokemon = Pokemon(
        name = pokemonEntity.name,
        id = pokemonEntity.id.addHash(),
        artwork = pokemonEntity.artwork,
        isFavorite = true
    )

}