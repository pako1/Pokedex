package com.example.pokedex.di

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.usecases.*
import com.example.pokedex.domain.usecases.base.FlowableUseCase
import com.example.pokedex.domain.usecases.base.UseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindAllPokemonsUseCase(getAllPokemonsUseCase: GetAllPokemonsUseCase): UseCase<List<Pokemon>?, AllPokemonsParams>

    @Binds
    abstract fun bindPokemonDetailUseCase(getPokemonDetailUseCase: GetPokemonDetailUseCase): UseCase<PokemonDetail?, PokemonDetailParams>

    @Binds
    abstract fun bindUpdateFavoritePokemonUseCase(updateFavoritePokemonUseCase: UpdateFavoritePokemonUseCase): UseCase<Unit, UpdateFavoritePokemonParams>

    @Binds
    abstract fun bindAllFavoritePokemonUseCase(getAllFavoritePokemonsUseCase: GetAllFavoritePokemonsUseCase): FlowableUseCase<List<Pokemon>, AllFavoritePokemonsParams>


}

