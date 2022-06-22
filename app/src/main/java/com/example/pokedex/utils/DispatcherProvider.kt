package com.example.pokedex.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.Unconfined
import javax.inject.Inject

/**
 * Will be used when we need to switch dispatchers
 */
interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}


class Dispatcher @Inject constructor() : DispatcherProvider {
    override val main: CoroutineDispatcher = Main
    override val io: CoroutineDispatcher = IO
    override val default: CoroutineDispatcher = Default
    override val unconfined: CoroutineDispatcher = Unconfined
}