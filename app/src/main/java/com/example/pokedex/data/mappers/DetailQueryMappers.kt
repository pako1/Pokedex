package com.example.pokedex.data.mappers

import com.apollographql.apollo3.api.ApolloResponse
import com.example.pokedex.PokemonQuery
import com.example.pokedex.domain.models.*
import javax.inject.Inject

class DetailQueryMappers @Inject constructor() {

    private fun unwrapTypes(pokemonTypes: List<PokemonQuery.Type?>?): List<Type> {
        return pokemonTypes?.let { types ->
            types.map { Type(it?.type?.name) }
        } ?: emptyList()
    }

    private fun unWrapMoves(pokemonMoves: List<PokemonQuery.Move?>?): List<Move> {
        return pokemonMoves?.let { moves ->
            moves.map { Move(it?.move?.name) }
        } ?: emptyList()
    }

    private fun unWrapGameIndices(gameIndexes: List<PokemonQuery.Game_index?>?): List<GameVersion> {
        return gameIndexes?.let { list ->
            list.map { GameVersion(it?.version?.name) }
        } ?: emptyList()
    }

    private fun unWrapStats(pokemonStats: List<PokemonQuery.Stat?>?): List<Stat> {
        return pokemonStats?.let { list ->
            list.map { pokemonStats ->
                Stat(
                    statValue = pokemonStats?.base_stat ?: 0,
                    statName = pokemonStats?.stat?.name ?: ""
                )
            }
        } ?: emptyList()
    }

    fun unwrapPokemonDetail(
        pokemonName: String,
        query: ApolloResponse<PokemonQuery.Data>
    ): PokemonDetail? {
        return query.data?.pokemon?.let { pokemon ->
            PokemonDetail(
                name = pokemonName,
                gameIndices = unWrapGameIndices(pokemon.game_indices),
                height = pokemon.height,
                moves = unWrapMoves(pokemon.moves),
                weight = pokemon.weight,
                types = unwrapTypes(pokemon.types),
                id = pokemon.id,
                stats = unWrapStats(pokemon.stats),
                artwork = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            )
        }
    }

}