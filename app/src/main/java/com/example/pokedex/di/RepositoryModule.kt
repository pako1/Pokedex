package com.example.pokedex.di

import com.example.pokedex.domain.repositories.DetailRepository
import com.example.pokedex.data.repo.DetailRepositoryImplementation
import com.example.pokedex.domain.repositories.PokemonsRepository
import com.example.pokedex.data.repo.PokemonsRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonsRepository(pokemonsRepository: PokemonsRepositoryImplementation): PokemonsRepository

    @Binds
    abstract fun bindDetailRepository(detailRepository: DetailRepositoryImplementation): DetailRepository
}