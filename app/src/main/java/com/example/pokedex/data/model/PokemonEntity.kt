package com.example.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    val id: Int,
    val artwork: String
)