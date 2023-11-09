package com.pessoto.bbcnews.feature.listheadlines.util

import com.pessoto.bbcnews.feature.listheadlines.data.model.ArticleResponse
import com.pessoto.bbcnews.feature.listheadlines.data.model.NewsResponse
import com.pessoto.bbcnews.feature.listheadlines.data.model.SourceResponse

internal fun mockNewsResponse(): NewsResponse {
    return NewsResponse(
        status = "ok", totalResults = 4, articles = listOf(
            ArticleResponse(
                source = SourceResponse(id = "bbc-news", name = "BBC News"),
                author = "BBC News",
                title = "BBC News 1",
                description = "BBC News Channel 1",
                url = "http://www.bbc.co.uk/news/world-asia-67371909",
                urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/15852/production/_131664188_gettyimages-1446238545.jpg",
                publishedAt = "2023-11-09T19:52:22.0508619Z",
                content = "Channel 3"
            ),
            ArticleResponse(
                source = SourceResponse(id = "bbc-news", name = "BBC News"),
                author = "BBC News",
                title = "BBC News 2",
                description = "BBC News Channel 2",
                url = "http://www.bbc.co.uk/news/world-asia-67371908",
                urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/15852/production/_131664188_gettyimages-1446238544.jpg",
                publishedAt = "2023-11-09T19:52:22.0508618Z",
                content = "Channel 3"
            ), ArticleResponse(
                source = SourceResponse(id = "bbc-news", name = "BBC News"),
                author = "BBC News",
                title = "BBC News 3",
                description = "BBC News Channel 3",
                url = "http://www.bbc.co.uk/news/world-asia-67371907",
                urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/15852/production/_131664188_gettyimages-1446238543.jpg",
                publishedAt = "2023-11-09T19:52:22.0508617Z",
                content = "Channel 3"
            )
        )
    )
}