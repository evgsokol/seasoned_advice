package com.evgeniasokolova.chefsadvices.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgeniasokolova.chefsadvices.domain.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
) : ViewModel() {

    private val _viewEvent = MutableLiveData<HomeUiEvent>()
    val viewEvent: LiveData<HomeUiEvent> = _viewEvent

    fun loadQuestions() {
        viewModelScope.launch {
            questionRepository.getQuestions()
                .onStart {
                    _viewEvent.value = HomeUiEvent.ShowLoading
                }.onCompletion {
                    _viewEvent.value = HomeUiEvent.HideLoading
                    _viewEvent.value  = HomeUiEvent.HideRefreshLayout
                }.catch {
                    Timber.e("Error value $it")
                    _viewEvent.value = HomeUiEvent.ShowError
                }.collect { repoList ->
                    _viewEvent.value = HomeUiEvent.ShowData(repoList)
                }
        }
    }
}