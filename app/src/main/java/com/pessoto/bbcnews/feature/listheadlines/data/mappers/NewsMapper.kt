package com.pessoto.bbcnews.feature.listheadlines.data.mappers

import com.pessoto.bbcnews.corearch.data.remote.mapper.Mapper
import com.pessoto.bbcnews.feature.listheadlines.data.model.ArticleResponse
import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.domain.model.News
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Source

internal class NewsMapper : Mapper<NewsResponse, News> {
    override fun map(source: NewsResponse): News {
        return News(
            status = source.status,
            totalResults = source.totalResults,
            articles = mapArticles(source.articles)
        )
    }

    private fun mapArticles(list: List<ArticleResponse>?): List<Article>? {
        return list?.map { article ->
            Article(
                source = Source(id = article.source.id, name = article.source.name),
                author = article.author,
                title = article.title,
                description = article.description,
                url = article.url,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content
            )
        }
    }
}