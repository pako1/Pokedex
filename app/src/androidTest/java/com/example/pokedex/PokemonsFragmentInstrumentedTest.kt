package com.example.pokedex

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.presentation.pokemons.PokemonsAdapter
import com.example.pokedex.presentation.pokemons.PokemonsFragment
import com.example.pokedex.presentation.pokemons.PokemonsState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class PokemonsFragmentInstrumentedTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun loading_state() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInState(navController, PokemonsState.Loading)
        onView(withId(R.id.pokemon_progress_bar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun network_error_state() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInState(navController, PokemonsState.Failure)
        onView(withId(R.id.pokemon_progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun success_state() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInState(
            navController,
            PokemonsState.Success(data = listOf(Pokemon("1", "bulbasaur", "")))
        )
        onView(withId(R.id.pokemon_progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun failure_state() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInState(navController, PokemonsState.Failure)
        onView(withId(R.id.pokemon_progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun pokemon_item_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInState(
            navController,
            PokemonsState.Success(data = listOf(Pokemon("1", "bulbasaur", "")))
        )
        onView(withId(R.id.pokemonRecycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonsAdapter.PokemonViewHolder>(
                0,
                click()
            )
        )
        assertEquals(navController.currentDestination?.id, R.id.detailFragment)
    }


    private fun <T : List<Pokemon>> launchFragmentInState(
        navController: TestNavHostController,
        state: PokemonsState<T>
    ) {
        launchFragmentInHiltContainer<PokemonsFragment>(null, R.style.AppTheme) {
            navController.setGraph(R.navigation.navigation)
            navController.setCurrentDestination(R.id.pokemonsFragment)
            Navigation.setViewNavController(requireView(), navController)
            renderUiState(state)
        }
    }
}