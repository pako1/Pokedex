package com.example.pokedex.di


import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import com.apollographql.apollo3.ApolloClient
import com.example.pokedex.data.datasource.local.database.Pokebase
import com.example.pokedex.utils.Constants.POKEMON_SERVER_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        Pokebase::class.java, "Pokebase.db"
    ).build()

    @Provides
    fun provideApolloClient() = ApolloClient.Builder().serverUrl(POKEMON_SERVER_URL).build()

    @Provides
    fun provideCoilImageLoader(
        @ApplicationContext context: Context,
        okHttpClientWithCache: OkHttpClient
    ) = ImageLoader
        .Builder(context)
        .crossfade(true)
        .okHttpClient(okHttpClientWithCache)
        .build()
}