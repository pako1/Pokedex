package com.example.pokedex.utils

import android.animation.ObjectAnimator
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.widget.ProgressBar
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

infix fun ProgressBar.reach(maxValue: Int) {
    val animation = ObjectAnimator.ofInt(this, "progress", 0, maxValue)
    animation.duration = Constants.ANIMATION_DURATION_IN_MS
    animation.interpolator = AccelerateInterpolator()
    animation.start()
}

fun <T> Fragment.collectSafelyOneFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest(collect)
    }
    //launchIn(CoroutineScope) will collect the flow in that specific scope.
    // When you do collect you suspend the coroutine until its canceled. So when you want
    // to do 2 collects or 1 collect and soemthing else you need to create another coroutine
}

fun Window.hideSystemUI() {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    WindowInsetsControllerCompat(this, this.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Window.showSystemUI() {
    WindowCompat.setDecorFitsSystemWindows(this, true)
    WindowInsetsControllerCompat(this, this.decorView).show(WindowInsetsCompat.Type.systemBars())
}

fun Int.addHash() = "#$this"

fun String.removeHash() = this.removePrefix("#").toInt()