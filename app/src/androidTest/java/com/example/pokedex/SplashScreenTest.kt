package com.example.pokedex

import android.content.Intent
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.pokedex.presentation.main.MainActivity
import com.example.pokedex.presentation.splash.SplashActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setup(){
        rule.inject()
    }

    @Test
    fun display_everything_properly() {
        launchActivity<SplashActivity>().use {
            val animationView = withId(R.id.animationView)
            val text = withId(R.id.text)
            onView(animationView).check(matches(isDisplayed()))
            with(onView(text)) {
                check(matches(isDisplayed()))
                check(matches(withText("Pokedex")))
                check(isCompletelyBelow(animationView))
                hasTextColor(R.color.white)
                Thread.sleep(2000)
            }
        }
    }

    @Test
    fun start_main_activity() {
        Intents.init()
        launchActivity<SplashActivity>().use {
            it.onActivity { activity ->
                val mainIntent = Intent(activity, MainActivity::class.java)
                activity.startActivity(mainIntent)
            }
            intended(hasComponent(MainActivity::class.java.name))
            onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
            pressBack()
            onView(withId(R.id.text)).check(matches(withText("Pokedex")))
            Thread.sleep(3000)
        }
    }

}