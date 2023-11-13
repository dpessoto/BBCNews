package com.pessoto.bbcnews.feature.listheadlines.data.repository

import app.cash.turbine.test
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.domain.exception.EmptyArticleListException
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsResponse
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsResponseWithoutArticle
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsWithoutArticles
import com.pessoto.bbcnews.feature.listheadlines.util.mockUnsortedArticles
import com.pessoto.bbcnews.feature.listheadlines.util.mockUnsortedNews
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class ListHeadlinesRepositoryImplTest {

    private val remoteDataSource = mockk<ListHeadlinesRemoteDataSource>()
    private val mapper = mockk<NewsMapper>()
    private val resource = mockk<ResourceProvider>()
    private val repository = ListHeadlinesRepositoryImpl(remoteDataSource, mapper, resource)

    @Test
    fun `getHeadlines Should return News When repository is success`() = runBlocking {
        // Given
        val unsortedNews = mockUnsortedNews()
        val valueExpected = mockUnsortedArticles()
        val valueMap = mockNewsResponse()
        every {
            remoteDataSource.getHeadlines()
        } returns flow { emit(valueMap) }
        every {
            mapper.map(valueMap)
        } returns unsortedNews

        // When
        val result = repository.getHeadlines()

        // Then
        result.test {
            assertEquals(valueExpected, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getHeadlines Should return Error When repository is failure`() = runBlocking {
        // Given
        every {
            remoteDataSource.getHeadlines()
        } returns flow { throw Throwable() }

        // When
        val result = repository.getHeadlines()

        // Then
        result.test {
            expectError()
        }
    }

    @Test
    fun `getHeadlines Should return Error When repository is List ArticleResponse is null`() =
        runBlocking {
            // Given
            val response = mockNewsResponseWithoutArticle()
            every { remoteDataSource.getHeadlines() } returns flow { emit(response) }
            every { resource.getString(R.string.empty_articles) } returns "Empty Articles"


            // When
            var exception: Throwable? = null
            try {
                repository.getHeadlines().collect{}
            } catch (e: Throwable) {
                exception = e
            }

            // Then
            assertEquals(EmptyArticleListException::class.java, exception?.javaClass)
            assertEquals("Empty Articles", exception?.message)
        }


    @Test
    fun `getHeadlines Should return Error When repository is List Article is null`() =
        runBlocking {
            // Given
            val response = mockNewsResponse()
            val newsWithoutArticle = mockNewsWithoutArticles()
            every { remoteDataSource.getHeadlines() } returns flow { emit(response) }
            every { mapper.map(response) } returns newsWithoutArticle
            every { resource.getString(R.string.empty_articles) } returns "Empty Articles"

            // When
            var exception: Throwable? = null
            try {
                repository.getHeadlines().collect{}
            } catch (e: Throwable) {
                exception = e
            }

            // Then
            assertEquals(EmptyArticleListException::class.java, exception?.javaClass)
            assertEquals("Empty Articles", exception?.message)
        }
}