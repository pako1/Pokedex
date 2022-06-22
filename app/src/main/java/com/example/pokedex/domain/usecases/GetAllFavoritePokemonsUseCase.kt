package com.example.pokedex.domain.usecases

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonsRepository
import com.example.pokedex.domain.usecases.base.FlowableUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritePokemonsUseCase @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
) : FlowableUseCase<@JvmSuppressWildcards List<Pokemon>,@JvmSuppressWildcards AllFavoritePokemonsParams> {

    override suspend fun invoke(params: AllFavoritePokemonsParams): Flow<List<Pokemon>> {
        return pokemonsRepository.getAllFavoritePokemons()
    }
}

object AllFavoritePokemonsParams