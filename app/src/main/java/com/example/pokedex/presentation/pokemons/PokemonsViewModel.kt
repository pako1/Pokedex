package com.example.pokedex.presentation.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.usecases.*
import com.example.pokedex.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val getAllPokemonsUseCase: GetAllPokemonsUseCase,
    private val getAllFavoritePokemonsUseCase: GetAllFavoritePokemonsUseCase,
    private val updateFavoritePokemonUseCase: UpdateFavoritePokemonUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _pokemons = MutableStateFlow<PokemonsState<List<Pokemon>>>(PokemonsState.Loading)
    val pokemons = _pokemons.asStateFlow()

    init {
        viewModelScope.launch {
            fetchPokemons()
        }
    }

    private suspend fun fetchPokemons() {
        val allPokemonResult = getAllPokemonsUseCase.invoke(AllPokemonsParams(20, 0))
        if (allPokemonResult.error != null) {
            Timber.e(allPokemonResult.error)
            _pokemons.value = PokemonsState.Failure
        } else {
            allPokemonResult.data?.let { pokemons ->
                favoritize(pokemons)
            } ?: PokemonsState.Success(data = emptyList<Pokemon>())
        }
    }

    private suspend fun favoritize(pokemonsList: List<Pokemon>) {
        getAllFavoritePokemonsUseCase.invoke(AllFavoritePokemonsParams)
            .collect { favoritePokemons ->
                pokemonsList.forEachIndexed { index, favorite ->

                    if (favoritePokemons.find { it.name == favorite.name } != null) {
                        val pokemon = pokemonsList.find { it.name == favorite.name }
                        val favoriteIndex = pokemonsList.indexOf(pokemon)
                        pokemonsList[favoriteIndex].isFavorite = true
                    } else {
                        pokemonsList[index].isFavorite = false
                    }
                }
                _pokemons.value = PokemonsState.Success(data = pokemonsList)
            }
    }

    fun updateFavorite(pokemon: Pokemon) {
        viewModelScope.launch(dispatcherProvider.io) {
            updateFavoritePokemonUseCase.invoke(UpdateFavoritePokemonParams(pokemon))
        }
    }

    fun retry() {
        viewModelScope.launch {
            _pokemons.value = PokemonsState.Loading
            fetchPokemons()
        }
    }
}
