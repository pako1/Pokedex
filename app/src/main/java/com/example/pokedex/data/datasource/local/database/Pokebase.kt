package com.example.pokedex.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex.data.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class Pokebase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
}
