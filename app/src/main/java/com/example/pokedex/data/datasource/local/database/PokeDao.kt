package com.example.pokedex.data.datasource.local.database

import androidx.room.*
import com.example.pokedex.data.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {
    @Query("SELECT * FROM pokemons")
    fun getFavoritePokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemons WHERE id IN (:pokemonId)")
    fun loadFavoritePokemonById(pokemonId: Int): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePokemons(vararg pokemonEntity: PokemonEntity)

    @Delete
    fun deletePokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT EXISTS(SELECT * FROM pokemons WHERE id = :pokemonId)")
    fun doesPokemonExistAlready(pokemonId : Int) : Boolean

}
