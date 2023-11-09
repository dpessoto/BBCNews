package com.pessoto.bbcnews.feature.listheadlines.data.source.remote

import com.pessoto.bbcnews.feature.listheadlines.data.api.ListHeadlinesService
import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ListHeadlinesRemoteDataSourceImpl(private val service: ListHeadlinesService) :
    ListHeadlinesRemoteDataSource {

    override fun getHeadlines(): Flow<NewsResponse> {
        return flow { emit(service.getHeadlines()) }
    }
}