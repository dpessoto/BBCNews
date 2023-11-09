package com.pessoto.bbcnews.feature.listheadlines.data.api

import com.pessoto.bbcnews.BuildConfig
import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ListHeadlinesService {

    @GET("top-headlines?sources={source}&apiKey={apiKey}")
    suspend fun getHeadlines(
        @Path("source") source: String = "bbc-news",
        @Path("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}