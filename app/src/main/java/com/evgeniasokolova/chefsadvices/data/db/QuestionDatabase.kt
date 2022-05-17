package com.evgeniasokolova.chefsadvices.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.evgeniasokolova.chefsadvices.data.api.Question

@Database(entities = [Question::class, FavoriteQuestion::class], version = 2, exportSchema = false)
@TypeConverters(OwnerConverter::class)
abstract class QuestionDatabase: RoomDatabase() {

    abstract val questionDao: QuestionDao

    abstract val favoriteQuestionDao: FavoriteQuestionDao
}