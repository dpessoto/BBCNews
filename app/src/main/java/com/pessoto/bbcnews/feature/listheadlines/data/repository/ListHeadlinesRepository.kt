package com.pessoto.bbcnews.feature.listheadlines.data.repository

import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import kotlinx.coroutines.flow.Flow


internal interface ListHeadlinesRepository {

    fun getHeadlines(): Flow<List<Article>>
}