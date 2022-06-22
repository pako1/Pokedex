package com.example.pokedex.data.repo

import com.apollographql.apollo3.exception.ApolloException
import com.example.pokedex.data.datasource.local.RoomSource
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.data.mappers.EntityMappers
import com.example.pokedex.data.mappers.PokemonQueryMappers
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repositories.PokemonsRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class PokemonsRepositoryImplementation @Inject constructor(
    private val graphQLSource: GraphQLSource,
    private val pokemonQueryMappers: PokemonQueryMappers,
    private val roomSource: RoomSource,
    private val entityMappers: EntityMappers
) : PokemonsRepository {

    override suspend fun retrievePokemons(
        limit: Int?,
        offset: Int?
    ): Response<List<Pokemon>> {
        return try {
            val query = graphQLSource.retrieveAllPokemon(limit = limit, offset = offset)
            if (query.hasErrors()) {
                Response.Failure(error = query.errors?.first())
            } else {
                Response.Success(data = pokemonQueryMappers.unwrapPokemon(query))
            }
        } catch (e: ApolloException) {
            // All network or GraphQL errors are handled here
            Response.NetworkError(throwable = e)
        }
    }

    override suspend fun getAllFavoritePokemons(): Flow<List<Pokemon>> {
        return roomSource.getFavoritePokemons().map { pokemonEntities ->
            pokemonEntities.map { pokemonEntity ->
                entityMappers.toModel(pokemonEntity)
            }
        }
    }

    override suspend fun updateFavoritePokemon(pokemon: Pokemon) {
        val pokemonEntity = entityMappers.toEntity(pokemon)
        if (roomSource.doesPokemonExistInFavorites(pokemonEntity)) {
            roomSource.undoFavoritePokemon(pokemonEntity)
        } else {
            roomSource.makeFavoritePokemon(pokemonEntity)
        }
    }
}

