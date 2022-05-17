package com.evgeniasokolova.chefsadvices.di

import com.evgeniasokolova.chefsadvices.BASE_URL
import com.evgeniasokolova.chefsadvices.BuildConfig
import com.evgeniasokolova.chefsadvices.data.api.QuestionsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient): QuestionsApiService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(QuestionsApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply { addInterceptor(logger) }.build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY

            }
        }
    }
}