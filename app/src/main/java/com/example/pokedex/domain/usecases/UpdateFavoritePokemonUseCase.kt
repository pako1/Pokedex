package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonsRepository
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.domain.usecases.base.UseCase
import javax.inject.Inject

class UpdateFavoritePokemonUseCase @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
) : UseCase<Unit, UpdateFavoritePokemonParams> {

    override suspend fun invoke(params: UpdateFavoritePokemonParams): Result<Unit> {
        pokemonsRepository.updateFavoritePokemon(params.pokemon)
        return Unit.asResult()
    }
}

data class UpdateFavoritePokemonParams(val pokemon: Pokemon)
