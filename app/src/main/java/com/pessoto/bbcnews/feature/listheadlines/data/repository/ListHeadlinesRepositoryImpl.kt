package com.pessoto.bbcnews.feature.listheadlines.data.repository

import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.domain.exception.EmptyArticleListException
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class ListHeadlinesRepositoryImpl(
    private val remoteDataSource: ListHeadlinesRemoteDataSource,
    private val mapper: NewsMapper,
    private val resourceProvider: ResourceProvider
) : ListHeadlinesRepository {

    @OptIn(FlowPreview::class)
    override fun getHeadlines(): Flow<List<Article>> {
        return remoteDataSource.getHeadlines().flatMapConcat { response ->
            if (response.articles.isNullOrEmpty()) {
                flow {
                    throw EmptyArticleListException(resourceProvider.getString(R.string.empty_articles))
                }
            } else {
                flow { emit(response) }
            }
        }.map(mapper::map).flatMapConcat { news ->
            if (news.articles.isNullOrEmpty()) throw EmptyArticleListException(
                resourceProvider.getString(
                    R.string.empty_articles
                )
            )
            else flow { emit(news.articles) }
        }
    }
}