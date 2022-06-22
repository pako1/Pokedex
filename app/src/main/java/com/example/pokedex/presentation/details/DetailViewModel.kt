package com.example.pokedex.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonDetails = MutableStateFlow<DetailState<PokemonDetail>>(DetailState.Loading)
    val pokemonDetails = _pokemonDetails.asStateFlow()

    private var name: String = ""

    fun getPokemonName(pokemonName: String) {
        viewModelScope.launch {
            name = pokemonName
            delay(2000)
            val result = getPokemonDetailUseCase.invoke(PokemonDetailParams(pokemonName))
            _pokemonDetails.value = if (result.data != null) {
                DetailState.Success(data = result.data)
            } else {
                Timber.e(result.error)
                DetailState.Failure
            }
        }
    }

    fun retry() {
        _pokemonDetails.value = DetailState.Loading
        getPokemonName(name)
    }
}