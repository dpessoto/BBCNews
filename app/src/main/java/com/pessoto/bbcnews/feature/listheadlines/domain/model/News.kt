package com.pessoto.bbcnews.feature.listheadlines.domain.model

internal data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)