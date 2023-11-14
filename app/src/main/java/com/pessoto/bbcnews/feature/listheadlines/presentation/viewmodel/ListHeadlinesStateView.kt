package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.presentation.model.ListHeadlineError

internal sealed class ListHeadlinesStateView {

    data object Loading : ListHeadlinesStateView()
    data class DataLoaded(val data: List<Article>) : ListHeadlinesStateView()
    data class Error(val error: ListHeadlineError) : ListHeadlinesStateView()
    data class GoToArticle(val article: Article) : ListHeadlinesStateView()
}