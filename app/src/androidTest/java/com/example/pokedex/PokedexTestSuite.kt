package com.example.pokedex

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    SplashScreenTest::class,
    PokemonsFragmentInstrumentedTest::class,
    DetailFragmentTestInstrumentedTest::class,
    OfflineMode::class,
    OnlineMode::class

)
class PokedexTestSuite