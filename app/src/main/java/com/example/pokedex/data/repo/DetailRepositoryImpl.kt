package com.example.pokedex.data.repo

import com.apollographql.apollo3.exception.ApolloException
import com.example.pokedex.data.datasource.remote.GraphQLSource
import com.example.pokedex.domain.repositories.DetailRepository
import com.example.pokedex.data.mappers.DetailQueryMappers
import com.example.pokedex.domain.models.*
import dagger.Reusable
import javax.inject.Inject

@Reusable
class DetailRepositoryImplementation @Inject constructor(
    private val detailQueryMappers: DetailQueryMappers,
    private val graphQLSource: GraphQLSource
) : DetailRepository {

    override suspend fun fetchPokemonDetails(pokemonName: String): Response<PokemonDetail> {
        return try {
            val query = graphQLSource.retrievePokemonDetails(pokemonName)
            if (query.hasErrors()) {
                Response.Failure(error = query.errors?.first())
            } else {
                val pokemonDetail = detailQueryMappers.unwrapPokemonDetail(pokemonName, query)
                Response.Success(data = pokemonDetail)
            }
        } catch (e: ApolloException) {
            // All network or GraphQL errors are handled here
            Response.NetworkError(throwable = e)
        }
    }
}

