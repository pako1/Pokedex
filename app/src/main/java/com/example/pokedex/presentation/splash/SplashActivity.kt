package com.example.pokedex.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.utils.Constants.SPLASH_DELAY
import com.example.pokedex.databinding.ActivitySplashBinding
import com.example.pokedex.presentation.main.MainActivity
import com.example.pokedex.utils.hideSystemUI
import com.example.pokedex.utils.showSystemUI
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.hideSystemUI()
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        val view = splashBinding.root
        setContentView(view)
        lifecycleScope.launchWhenCreated {
            delay(SPLASH_DELAY)
            splashBinding.animationView.pauseAnimation()
            window.showSystemUI()
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}

