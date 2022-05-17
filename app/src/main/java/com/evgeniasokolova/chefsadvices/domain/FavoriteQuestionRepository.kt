package com.evgeniasokolova.chefsadvices.domain

import com.evgeniasokolova.chefsadvices.data.db.FavoriteQuestion
import com.evgeniasokolova.chefsadvices.data.db.FavoriteQuestionDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteQuestionRepository @Inject constructor(
    private val favoriteDao: FavoriteQuestionDao,
) {

    fun isFavorite(id: String): Boolean = favoriteDao.isFavorite(id)

    fun createFavoriteQuestion(id: String) {
        val question = FavoriteQuestion(id, "title", "body")
        favoriteDao.insertFavoriteQuestion(question)
    }

    fun getFavoriteQuestions() {
        favoriteDao.getFavoriteQuestions()
    }

    fun removeQuestion(question: FavoriteQuestion) {
        favoriteDao.deleteFavoriteQuestion(question)
    }
}