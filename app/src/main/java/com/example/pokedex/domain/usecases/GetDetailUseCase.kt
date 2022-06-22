package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.repositories.DetailRepository
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.domain.usecases.base.UseCase
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val responseToResultHelper: ResponseToResultHelper
) : UseCase<@JvmSuppressWildcards PokemonDetail?, @JvmSuppressWildcards PokemonDetailParams> {
    override suspend fun invoke(params: PokemonDetailParams): Result<PokemonDetail?> {
        val response = detailRepository.fetchPokemonDetails(params.pokemonName)
        return responseToResultHelper.mapToResult(response)
    }
}

data class PokemonDetailParams(val pokemonName: String)