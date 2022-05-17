package com.evgeniasokolova.chefsadvices.di

import com.evgeniasokolova.chefsadvices.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    fun provideDispatcher(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers.create()
    }
}