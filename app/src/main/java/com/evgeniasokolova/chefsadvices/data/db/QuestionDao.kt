package com.evgeniasokolova.chefsadvices.data.db

import androidx.room.*
import com.evgeniasokolova.chefsadvices.data.api.Question

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions")
    suspend fun getQuestions(): List<Question>

    @Query("SELECT * FROM questions WHERE question_id = :questionId")
    suspend fun getQuestionById(questionId: String): Question

    @Update
    fun update(question: Question)

    @Delete
    fun delete(question: Question)

    @Update
    suspend fun updateQuestion(questionId: Question)

//    @Query("SELECT EXISTS(SELECT 1 FROM favorite_table WHERE id = :id LIMIT 1)")
//    fun isFavorite(id: String): Boolean
}