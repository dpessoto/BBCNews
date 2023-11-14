package com.pessoto.bbcnews.feature.article.presentation.viewmodel

import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article

internal sealed class ArticleStateView {

    data class DataLoaded(val data: Article) : ArticleStateView()
    data class Error(val message: String) : ArticleStateView()
}