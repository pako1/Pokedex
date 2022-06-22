package com.example.pokedex.di

import com.example.pokedex.utils.Dispatcher
import com.example.pokedex.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    @Singleton
    abstract fun provideDispatchers(dispatcher: Dispatcher): DispatcherProvider
}