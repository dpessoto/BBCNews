package com.pessoto.bbcnews.feature.listheadlines.data.source.remote

import app.cash.turbine.test
import com.pessoto.bbcnews.feature.listheadlines.data.api.ListHeadlinesService
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ListHeadlinesRemoteDataSourceImplTest {

    private val service = mockk<ListHeadlinesService>()
    private val remoteDataSource = ListHeadlinesRemoteDataSourceImpl(service)

    @Test
    fun `getHeadlines Should return NewsResponse When service is success`() = runBlocking {
        // Given
        val expectedResult = mockNewsResponse()
        coEvery { service.getHeadlines() } returns expectedResult

        // When
        val result = remoteDataSource.getHeadlines()

        // Then
        result.test {
            assertEquals(expectedResult, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getHeadlines Should return throwable When service is failure`() = runBlocking {
        // Given
        coEvery { service.getHeadlines() } throws Throwable()

        // When
        val result = remoteDataSource.getHeadlines()

        // Then
        result.test {
            assertEquals(Throwable::class.java, expectError()::class.java)
        }
    }
}