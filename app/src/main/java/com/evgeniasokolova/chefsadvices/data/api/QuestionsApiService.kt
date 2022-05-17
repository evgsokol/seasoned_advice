package com.evgeniasokolova.chefsadvices.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsApiService {

    @GET("questions?filter=withbody&sort=creation&site=cooking")
    suspend fun getQuestions(
        @Query("pagesize") pagesize: Int?,
        @Query("page") page: Int?,
    ): QuestionList
}