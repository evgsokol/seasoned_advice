package com.evgeniasokolova.chefsadvices

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppCoroutineDispatchers {
    val ioDispatcher: CoroutineDispatcher

    companion object {
        fun create() = object : AppCoroutineDispatchers {
            override val ioDispatcher: CoroutineDispatcher
                get() = Dispatchers.IO
        }
    }
}