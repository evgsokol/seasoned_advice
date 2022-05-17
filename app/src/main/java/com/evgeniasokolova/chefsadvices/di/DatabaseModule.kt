package com.evgeniasokolova.chefsadvices.di

import android.content.Context
import androidx.room.Room
import com.evgeniasokolova.chefsadvices.data.db.FavoriteQuestionDao
import com.evgeniasokolova.chefsadvices.data.db.OwnerConverter
import com.evgeniasokolova.chefsadvices.data.db.QuestionDao
import com.evgeniasokolova.chefsadvices.data.db.QuestionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        converter: OwnerConverter,
    ): QuestionDatabase {
        return Room.databaseBuilder(
            appContext,
            QuestionDatabase::class.java, "question_database"
        )
            .addTypeConverter(converter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideQuestionDao(appDatabase: QuestionDatabase): QuestionDao {
        return appDatabase.questionDao
    }

    @Provides
    @Singleton
    fun provideFavoriteQuestionDao(appDatabase: QuestionDatabase): FavoriteQuestionDao {
        return appDatabase.favoriteQuestionDao
    }
}