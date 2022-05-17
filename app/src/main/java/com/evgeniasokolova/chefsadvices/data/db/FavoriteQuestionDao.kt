package com.evgeniasokolova.chefsadvices.data.db

import androidx.room.*

@Dao
interface FavoriteQuestionDao {

    @Query("SELECT * FROM favorite_table")
   fun getFavoriteQuestions(): List<FavoriteQuestion>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_table WHERE id = :id LIMIT 1)")
    fun isFavorite(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteQuestion(question: FavoriteQuestion)

    @Delete
    fun deleteFavoriteQuestion(question: FavoriteQuestion)

}