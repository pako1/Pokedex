package com.example.pokedex

import androidx.core.widget.ContentLoadingProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.pokedex.domain.models.Move
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.Stat
import com.example.pokedex.domain.models.Type
import com.example.pokedex.presentation.details.DetailFragment
import com.example.pokedex.presentation.details.DetailState
import com.example.pokedex.presentation.main.MainActivity
import com.example.pokedex.utils.Constants.ATTACK
import com.example.pokedex.utils.Constants.DEFENSE
import com.example.pokedex.utils.Constants.HP
import com.example.pokedex.utils.Constants.SPEED
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
class DetailFragmentTestInstrumentedTest {

    @get:Rule(order = 1)
    val rule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun loading_state() {
        launchFragmentInHiltContainer<DetailFragment>(null, R.style.AppTheme) {
            renderUiState(DetailState.Loading)
        }
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        Thread.sleep(2000)

    }

    @Test
    fun network_error_state() {
        launchFragmentInHiltContainer<DetailFragment>(null, R.style.AppTheme) {
            renderUiState(DetailState.Failure)
        }
        Thread.sleep(2000)
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.error_state)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun success_state_with_single_type() {
        var hpBar: ContentLoadingProgressBar? = null
        var attackBar: ContentLoadingProgressBar? = null
        var defenseBar: ContentLoadingProgressBar? = null
        var speedBar: ContentLoadingProgressBar? = null
        val successResponse = DetailState.Success(
            data = PokemonDetail(
                id = 1,
                name = "bulbasaur",
                gameIndices = null,
                height = 12,
                weight = 40,
                moves = listOf(Move("attack")),
                stats = listOf(
                    Stat(12, HP),
                    Stat(80, ATTACK),
                    Stat(13, DEFENSE),
                    Stat(90, SPEED)
                ),
                types = listOf(Type("poison")),
                artwork = ""
            )
        )
        launchFragmentInHiltContainer<DetailFragment>(null, R.style.AppTheme) {
            renderUiState(successResponse)
            hpBar = requireView().findViewById(R.id.hp_progress)
            assertEquals(hpBar?.progress, 0)
            attackBar = requireView().findViewById(R.id.attack_progress)
            assertEquals(attackBar?.progress, 0)
            defenseBar = requireView().findViewById(R.id.defense_progress)
            assertEquals(defenseBar?.progress, 0)
            speedBar = requireView().findViewById(R.id.speed_progress)
            assertEquals(speedBar?.progress, 0)
        }
        Thread.sleep(3000)

        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.hp_progress)).check(matches(isDisplayed()))
        assertEquals(hpBar?.progress, 12)
        assertEquals(attackBar?.progress, 80)
        assertEquals(defenseBar?.progress, 13)
        assertEquals(speedBar?.progress, 90)

        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.attack_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.defense_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.speed_progress)).check(matches(isDisplayed()))
        testDependingOnType(successResponse.data.types)
        onView(withId(R.id.name)).check(matches(withText("bulbasaur")))
        onView(withId(R.id.height_label)).check(matches(isDisplayed()))
        onView(withId(R.id.height_value)).check(matches(withText("12")))
        onView(withId(R.id.weight_label)).check(matches(isDisplayed()))
        onView(withId(R.id.weight_value)).check(matches(withText("40")))
        onView(withId(R.id.order)).check(matches(withText("#1")))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun success_state_with_two_types() {
        var hpBar: ContentLoadingProgressBar? = null
        var attackBar: ContentLoadingProgressBar? = null
        var defenseBar: ContentLoadingProgressBar? = null
        var speedBar: ContentLoadingProgressBar? = null
        val successResponse = DetailState.Success(
            data = PokemonDetail(
                name = "bulbasaur",
                gameIndices = null,
                height = 12,
                weight = 40,
                moves = listOf(Move("attack")),
                id = 1,
                stats = listOf(
                    Stat(12, HP),
                    Stat(80, ATTACK),
                    Stat(13, DEFENSE),
                    Stat(90, SPEED)
                ),
                types = listOf(Type("poison"), Type("grass")),
                artwork = ""
            )
        )
        launchFragmentInHiltContainer<DetailFragment>(null, R.style.AppTheme) {
            hpBar = requireView().findViewById(R.id.hp_progress)
            assertEquals(hpBar?.progress, 0)
            attackBar = requireView().findViewById(R.id.attack_progress)
            assertEquals(attackBar?.progress, 0)
            defenseBar = requireView().findViewById(R.id.defense_progress)
            assertEquals(defenseBar?.progress, 0)
            speedBar = requireView().findViewById(R.id.speed_progress)
            assertEquals(speedBar?.progress, 0)
            renderUiState(successResponse)
        }
        Thread.sleep(3000)

        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.hp_progress)).check(matches(isDisplayed()))
        assertEquals(hpBar?.progress, 12)
        assertEquals(attackBar?.progress, 80)
        assertEquals(defenseBar?.progress, 13)
        assertEquals(speedBar?.progress, 90)

        onView(withId(R.id.attack_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.defense_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.speed_progress)).check(matches(isDisplayed()))
        testDependingOnType(successResponse.data.types)
        onView(withId(R.id.name)).check(matches(withText("bulbasaur")))
        onView(withId(R.id.height_label)).check(matches(isDisplayed()))
        onView(withId(R.id.height_value)).check(matches(withText("12")))
        onView(withId(R.id.weight_label)).check(matches(isDisplayed()))
        onView(withId(R.id.weight_value)).check(matches(withText("40")))
        onView(withId(R.id.order)).check(matches(withText("#1")))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    private fun testDependingOnType(types: List<Type?>?) {
        when (types?.size) {
            1 -> {
                onView(withId(R.id.first_type)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
                onView(withId(R.id.first_type_text)).check(matches(withText("poison")))
                onView(withId(R.id.second_type_text)).check(
                    matches(
                        withEffectiveVisibility(
                            Visibility.GONE
                        )
                    )
                )
                onView(withId(R.id.second_type)).check(matches(withEffectiveVisibility(Visibility.GONE)))
            }
            2 -> {
                onView(withId(R.id.first_type)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
                onView(withId(R.id.first_type_text)).check(matches(withText("poison")))
                onView(withId(R.id.second_type_text)).check(
                    matches(
                        withEffectiveVisibility(
                            Visibility.VISIBLE
                        )
                    )
                )
                onView(withId(R.id.second_type)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
                onView(withId(R.id.second_type_text)).check(matches(withText("grass")))

            }
        }
    }

    @Test
    fun failure_state() {
        launchFragmentInHiltContainer<DetailFragment>(null, R.style.AppTheme) {
            renderUiState(DetailState.Failure)
        }
        Thread.sleep(2000)
        onView(withId(R.id.content)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.error_state)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.group)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

}