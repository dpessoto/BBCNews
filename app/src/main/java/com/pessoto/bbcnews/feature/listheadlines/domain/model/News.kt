package com.pessoto.bbcnews.feature.listheadlines.domain.model

internal data class News(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>? = null
)