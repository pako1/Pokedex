package com.example.pokedex

import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.pokedex.presentation.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class OfflineMode {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setup() {
        rule.inject()

    }

    @Test
    fun offline_mode() {
        setAirplaneMode(true)
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchActivity<MainActivity>().onActivity {
            navController.setGraph(R.navigation.navigation)
            navController.setCurrentDestination(R.id.pokemonsFragment)
            Navigation.setViewNavController(it.findViewById(R.id.nav_host_fragment), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.pokemon_progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.pokemonRecycler))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.pokemon_progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.group))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.retry_button)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.pokemon_progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.group))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.group))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Thread.sleep(3000)
        setAirplaneMode(false)
    }


    private fun setAirplaneMode(enable: Boolean) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).run {
            context.packageManager.getLaunchIntentForPackage("com.android.settings")
                ?.let { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    findObject(UiSelector().textContains("Network")).clickAndWaitForNewWindow()
                    if (!findObject(UiSelector().textMatches("Airplane mode")).isEnabled && enable) {
                        findObject(UiSelector().textMatches("Airplane mode")).click()
                    } else {
                        findObject(UiSelector().textMatches("Airplane mode")).click()
                    }
                }
        }
    }
}