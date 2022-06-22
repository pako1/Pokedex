package com.example.pokedex.domain.models

data class PokemonDetail(
    val id: Int?,
    val name: String,
    val artwork : String,
    val gameIndices: List<GameVersion?>?,
    val height: Int?,
    val moves: List<Move?>?,
    val weight: Int?,
    val types: List<Type?>?,
    val stats: List<Stat>
)