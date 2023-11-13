package com.pessoto.bbcnews.feature.listheadlines.domain.usecase

import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepository
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

internal class GetHeadLinesUseCase(
    private val repository: ListHeadlinesRepository,
    private val dateFormat: SimpleDateFormat = SimpleDateFormat(
        DATE_PATTERN,
        Locale.getDefault()
    )
) {

    operator fun invoke(): Flow<List<Article>> = repository.getHeadlines().map {
        it.sortedByDescending { article ->
            val date = dateFormat.parse(article.publishedAt)
            date
        }
    }
}