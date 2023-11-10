package com.pessoto.bbcnews.feature.listheadlines.data.api

import com.pessoto.bbcnews.BuildConfig
import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ListHeadlinesService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("sources") source: String = "bbc-news",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}