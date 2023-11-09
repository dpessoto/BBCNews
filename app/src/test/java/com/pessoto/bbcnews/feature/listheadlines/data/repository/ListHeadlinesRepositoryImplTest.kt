package com.pessoto.bbcnews.feature.listheadlines.data.repository

import app.cash.turbine.test
import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.util.mockNews
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsResponse
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
    private val repository = ListHeadlinesRepositoryImpl(remoteDataSource, mapper)

    @Test
    fun `getHeadlines Should return News When repository is success`() = runBlocking {
        // Given
        val valueExpected = mockNews()
        val valueMap = mockNewsResponse()
        every {
            remoteDataSource.getHeadlines()
        } returns flow { emit(valueMap) }
        every {
            mapper.map(valueMap)
        } returns valueExpected

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
}