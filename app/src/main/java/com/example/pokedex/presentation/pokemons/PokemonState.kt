package com.example.pokedex.presentation.pokemons

sealed class PokemonsState<out T> {
    object Loading : PokemonsState<Nothing>()
    object Failure : PokemonsState<Nothing>()
    data class Success<T>(val data: T) : PokemonsState<T>()
}
