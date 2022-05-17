package com.evgeniasokolova.chefsadvices.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgeniasokolova.chefsadvices.domain.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    fun getQuestion(questionId: String) {
        viewModelScope.launch {
            repository.getQuestion(questionId)
        }
    }

}