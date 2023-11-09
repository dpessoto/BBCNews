package com.pessoto.bbcnews.feature.listheadlines.domain.usecase

import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepository
import com.pessoto.bbcnews.feature.listheadlines.domain.model.News
import kotlinx.coroutines.flow.Flow

internal class GetHeadLinesUseCase(private val repository: ListHeadlinesRepository) {

    operator fun invoke(): Flow<News> = repository.getHeadlines()
}