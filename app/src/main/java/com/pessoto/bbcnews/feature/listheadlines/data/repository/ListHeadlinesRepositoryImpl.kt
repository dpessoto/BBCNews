package com.pessoto.bbcnews.feature.listheadlines.data.repository

import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ListHeadlinesRepositoryImpl(
    private val remoteDataSource: ListHeadlinesRemoteDataSource,
    private val mapper: NewsMapper
) : ListHeadlinesRepository {

    override fun getHeadlines(): Flow<News> {
        val response = remoteDataSource.getHeadlines()
        return response.map(mapper::map)
    }
}