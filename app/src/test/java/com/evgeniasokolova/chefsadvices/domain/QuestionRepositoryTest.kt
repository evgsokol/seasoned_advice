package com.evgeniasokolova.chefsadvices.domain


import com.evgeniasokolova.chefsadvices.AppTestCoroutineDispatchers
import com.evgeniasokolova.chefsadvices.QUERY_PAGE
import com.evgeniasokolova.chefsadvices.QUERY_PAGE_SIZE
import com.evgeniasokolova.chefsadvices.data.api.Owner
import com.evgeniasokolova.chefsadvices.data.api.Question
import com.evgeniasokolova.chefsadvices.data.api.QuestionList
import com.evgeniasokolova.chefsadvices.data.api.QuestionsApiService
import com.evgeniasokolova.chefsadvices.data.db.QuestionDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionRepositoryTest {

    private val firstQuestion = Question("123", "title", "body", owner = Owner("url","name"))
    private val secondQuestion = Question("456", "title2", "body2", owner = Owner("url2","name2"))
    private val dummyQuestionList = QuestionList(listOf(firstQuestion, secondQuestion))

    private lateinit var testedRepository: QuestionRepository
    private val serviceMock = mockk<QuestionsApiService>()
    private val fakeDao = mockk<QuestionDao>(relaxed = true)

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        testedRepository = QuestionRepository(serviceMock, AppTestCoroutineDispatchers(testCoroutineDispatcher), fakeDao)
    }

    @Test
    fun getQuestionsIsSucceeded() = runTest(testCoroutineDispatcher) {
        coEvery { serviceMock.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE) } returns dummyQuestionList

        val result = testedRepository.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE)

        result.collect { questionsList ->
            assertEquals(questionsList,  dummyQuestionList)
        }
        coVerify { fakeDao.insertQuestions(listOf(firstQuestion, secondQuestion)) }

    }

    @Test(expected = IOException::class)
    fun getQuestionsIsFailed() = runTest(testCoroutineDispatcher) {
        coEvery { serviceMock.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE) } throws IOException()

        val flow = testedRepository.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE)

        flow.collect { questionsList ->
            assertEquals(questionsList.items,  emptyList())
        }
    }
}