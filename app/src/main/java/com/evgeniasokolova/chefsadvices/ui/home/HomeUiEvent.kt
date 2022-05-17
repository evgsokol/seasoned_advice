package com.evgeniasokolova.chefsadvices.ui.home

import com.evgeniasokolova.chefsadvices.data.api.QuestionList

sealed class HomeUiEvent {
    data class ShowData(val data: QuestionList) : HomeUiEvent()
    object ShowError : HomeUiEvent()
    object ShowLoading : HomeUiEvent()
    object HideLoading : HomeUiEvent()
    object HideRefreshLayout: HomeUiEvent()
}