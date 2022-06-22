package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonsRepository
import com.example.pokedex.domain.usecases.base.Result
import com.example.pokedex.domain.usecases.base.UseCase
import javax.inject.Inject

class GetAllPokemonsUseCase @Inject constructor(
    private val pokemonsRepository: PokemonsRepository,
    private val responseToResultHelper: ResponseToResultHelper,
) : UseCase<@JvmSuppressWildcards List<Pokemon>?, @JvmSuppressWildcards AllPokemonsParams> {
    override suspend fun invoke(params: AllPokemonsParams): Result<List<Pokemon>?> {
        val response = pokemonsRepository.retrievePokemons(params.limit, params.offset)
        return responseToResultHelper.mapToResult(response)
    }
}

data class AllPokemonsParams(val limit: Int, val offset: Int)