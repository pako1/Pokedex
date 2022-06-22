package com.example.pokedex.repository.data

import com.example.pokedex.PokemonListQuery
import com.example.pokedex.PokemonQuery
import com.example.pokedex.domain.models.*

class FakeQueryDataProvider {

    val singlePokemon = Pokemon(
        "#1",
        "bulbasaur",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        false
    )

    val multiplePokemon = listOf(
        Pokemon(
            "#1",
            "bulbasaur",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            false
        ),
        Pokemon(
            "#2",
            "ivysaur",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
            false
        ),
        Pokemon(
            "#3",
            "venusaur",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            false
        ),
        Pokemon(
            "#4",
            "charmander",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
            false
        ), Pokemon(
            "#5",
            "charmeleon",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
            false
        ),
        Pokemon(
            "#6",
            "charizard",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
            false
        )
    )
    val pokemonDetail = PokemonDetail(
        name = "bulbasaur",
        gameIndices = listOf(GameVersion("red"), GameVersion("blue"), GameVersion("yellow")),
        height = 20,
        moves = listOf(Move("razor wind")),
        weight = 20,
        types = listOf(Type("grass"), Type("poison")),
        id = 1,
        stats = listOf(
            Stat(45, "hp"),
            Stat(45, "speed"),
            Stat(45, "attack"),
            Stat(45, "defense")
        ),
        artwork = ""
    )
    val singleDetailPokemonData = PokemonQuery.Data(
        pokemon = PokemonQuery.Pokemon(
            game_indices = listOf(
                PokemonQuery.Game_index(PokemonQuery.Version("red")),
                PokemonQuery.Game_index(PokemonQuery.Version("blue")),
                PokemonQuery.Game_index(PokemonQuery.Version("yellow")),
            ),
            height = 20,
            moves = listOf(PokemonQuery.Move(move = PokemonQuery.Move1("razor wind"))),
            weight = 20,
            types = listOf(
                PokemonQuery.Type(PokemonQuery.Type1("grass")),
                PokemonQuery.Type(PokemonQuery.Type1("poison"))
            ),
            id = 1,
            stats = listOf(
                PokemonQuery.Stat(45, PokemonQuery.Stat1("hp")),
                PokemonQuery.Stat(45, PokemonQuery.Stat1("speed")),
                PokemonQuery.Stat(45, PokemonQuery.Stat1("attack")),
                PokemonQuery.Stat(45, PokemonQuery.Stat1("defense"))
            )
        )
    )

    val singlePokemonListData = PokemonListQuery.Data(
        pokemons = PokemonListQuery.Pokemons(
            results = listOf(
                PokemonListQuery.Result(
                    1,
                    "bulbasaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                )
            )
        )
    )

    val multiplePokemonListData = PokemonListQuery.Data(
        pokemons = PokemonListQuery.Pokemons(
            results = listOf(
                PokemonListQuery.Result(
                    1,
                    "bulbasaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                ),
                PokemonListQuery.Result(
                    2,
                    "ivysaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png"
                ),
                PokemonListQuery.Result(
                    3,
                    "venusaur",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png"
                ),
                PokemonListQuery.Result(
                    4,
                    "charmander",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"
                ),
                PokemonListQuery.Result(
                    5,
                    "charmeleon",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png"
                ),
                PokemonListQuery.Result(
                    6,
                    "charizard",
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png"
                )
            )
        )
    )
}