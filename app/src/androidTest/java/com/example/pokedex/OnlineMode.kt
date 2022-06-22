package com.example.pokedex

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.pokedex.presentation.main.MainActivity
import com.example.pokedex.presentation.pokemons.PokemonsAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class OnlineMode {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        rule.inject()
    }

    @After
    fun cleanup() {
        activityScenarioRule.scenario.close()
    }

    @Test
    fun display_everything_properly() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        activityScenarioRule.scenario.onActivity {
            navController.setGraph(R.navigation.navigation)
            navController.setCurrentDestination(R.id.pokemonsFragment)
            Navigation.setViewNavController(it.findViewById(R.id.nav_host_fragment), navController)
        }
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.pokemon_progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.pokemonRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.pokemonRecycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonsAdapter.PokemonViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Thread.sleep(3000)
        pressBack()
        Thread.sleep(3000)
    }
}