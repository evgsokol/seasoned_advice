package com.evgeniasokolova.chefsadvices.data

import com.evgeniasokolova.chefsadvices.data.api.QuestionList

sealed class NetworkResult<T> {
    data class NetworkSuccess<T>(val data: QuestionList) : NetworkResult<T>()
    data class NetworkError<T>(val error: Throwable, val data: T? = null) : NetworkResult<T>()
}

val NetworkResult<*>.succeeded
    get() = this is NetworkResult.NetworkSuccess

val NetworkResult<*>.failed: Boolean
    get() = this is NetworkResult.NetworkError