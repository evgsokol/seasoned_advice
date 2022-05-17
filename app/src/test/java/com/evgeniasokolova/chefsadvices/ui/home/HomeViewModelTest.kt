package com.evgeniasokolova.chefsadvices.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.evgeniasokolova.chefsadvices.QUERY_PAGE
import com.evgeniasokolova.chefsadvices.QUERY_PAGE_SIZE
import com.evgeniasokolova.chefsadvices.data.api.Owner
import com.evgeniasokolova.chefsadvices.data.api.Question
import com.evgeniasokolova.chefsadvices.data.api.QuestionList
import com.evgeniasokolova.chefsadvices.domain.QuestionRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repositoryMock = mockk<QuestionRepository>(relaxed = true)

    private val uiEventObserver: Observer<HomeUiEvent> = mockk(relaxed = true)

    private lateinit var homeViewModel: HomeViewModel

    private val firstQuestion = Question("123", "title", "body",
        owner = Owner("url", "name"))
    private val secondQuestion = Question("456", "title2", "body2",
        owner = Owner("url2", "name2"))
    private val testingQuestionList = QuestionList(listOf(firstQuestion, secondQuestion))

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        homeViewModel = HomeViewModel(repositoryMock).apply {
            viewEvent.observeForever(uiEventObserver)
        }
    }

    @Test
    fun loadQuestionsSuccess() = runTest(testCoroutineDispatcher) {
        val channel = Channel<QuestionList>()
        val flow = channel.consumeAsFlow()

        coEvery { repositoryMock.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE) } returns flow

        launch {
            channel.send(testingQuestionList)
        }

        homeViewModel.loadQuestions()

        verifyOrder {
            uiEventObserver.onChanged(HomeUiEvent.ShowLoading)
            uiEventObserver.onChanged(HomeUiEvent.ShowData(testingQuestionList))
        }
    }

    @Test
    fun loadQuestionsError() = runTest(testCoroutineDispatcher) {
        val channel = Channel<QuestionList>()
        val flow = channel.consumeAsFlow()

        coEvery { repositoryMock.getQuestions(QUERY_PAGE_SIZE, QUERY_PAGE) } returns flow

        launch {
            channel.send(testingQuestionList)
            channel.close(IOException())
        }

        homeViewModel.loadQuestions()

        verify { uiEventObserver.onChanged(HomeUiEvent.ShowError) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        homeViewModel.viewEvent.removeObserver(uiEventObserver)
    }
}