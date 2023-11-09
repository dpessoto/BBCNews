package com.pessoto.bbcnews.feature.listheadlines.data.source.remote

import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

internal interface ListHeadlinesRemoteDataSource {

    fun getHeadlines() : Flow<NewsResponse>
}