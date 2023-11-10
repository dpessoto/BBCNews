package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import com.pessoto.bbcnews.feature.listheadlines.domain.model.News

internal sealed class ListHeadlinesStateView {

    data object Loading : ListHeadlinesStateView()
    data class DataLoaded(val data: News) : ListHeadlinesStateView()
    data class Error(val e: Throwable) : ListHeadlinesStateView()
}