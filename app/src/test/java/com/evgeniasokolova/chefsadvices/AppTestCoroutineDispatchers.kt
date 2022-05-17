package com.evgeniasokolova.chefsadvices

import kotlinx.coroutines.CoroutineDispatcher

class AppTestCoroutineDispatchers(private val coroutineDispatcher: CoroutineDispatcher): AppCoroutineDispatchers {
    override val ioDispatcher: CoroutineDispatcher
        get() = coroutineDispatcher
}