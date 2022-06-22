package com.example.pokedex.di

import com.example.pokedex.data.datasource.local.RoomSource
import com.example.pokedex.data.datasource.local.RoomSourceImpl
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.datasource.remote.GraphQLSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindGraphQLSource(graphQLSourceImpl: GraphQLSourceImpl): GraphQLSource

    @Binds
    abstract fun bindRoomSource(roomSourceImpl: RoomSourceImpl): RoomSource

}