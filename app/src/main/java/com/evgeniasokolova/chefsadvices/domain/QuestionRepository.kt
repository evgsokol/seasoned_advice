package com.evgeniasokolova.chefsadvices.domain

import com.evgeniasokolova.chefsadvices.AppCoroutineDispatchers
import com.evgeniasokolova.chefsadvices.QUERY_PAGE
import com.evgeniasokolova.chefsadvices.QUERY_PAGE_SIZE
import com.evgeniasokolova.chefsadvices.data.api.Question
import com.evgeniasokolova.chefsadvices.data.api.QuestionList
import com.evgeniasokolova.chefsadvices.data.api.QuestionsApiService
import com.evgeniasokolova.chefsadvices.data.db.QuestionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val apiService: QuestionsApiService,
    private val dispatchers: AppCoroutineDispatchers,
    private val dao: QuestionDao,
) {

    fun getQuestions(
        pageSize: Int = QUERY_PAGE_SIZE,
        page: Int? = QUERY_PAGE,
    ): Flow<QuestionList> {
        return flow {
            val questions = apiService.getQuestions(pageSize, page)
            Timber.d("getQuestions() $questions")
            emit(questions)
            insertQuestions(questions.items)
        }
            .flowOn(dispatchers.ioDispatcher)
    }

    suspend fun getQuestion(id: String) = dao.getQuestionById(id)

    private suspend fun insertQuestions(questions: List<Question>) = dao.insertQuestions(questions)

    suspend fun getQuestionsFromDb() = dao.getQuestions()
}