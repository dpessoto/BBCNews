package com.pessoto.bbcnews.feature.listheadlines.domain.usecase

import app.cash.turbine.test
import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepository
import com.pessoto.bbcnews.feature.listheadlines.domain.model.News
import com.pessoto.bbcnews.feature.listheadlines.util.mockNews
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFails
import kotlin.time.ExperimentalTime


@ExperimentalTime
internal class GetHeadLinesUseCaseTest {

    private val repository = mockk<ListHeadlinesRepository>()
    private val useCase = GetHeadLinesUseCase(repository)

    @Test
    fun `getHeadLinesUseCase Should return News When repository is success`() = runBlocking {
        // Given
        val expectedResult = flow<News> { mockNews() }
        every { repository.getHeadlines() } returns expectedResult

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            assertEquals(expectedResult, result)
            expectComplete()
        }
    }

    @Test
    fun `getHeadLinesUseCase Should throw throwable When repository is failure`() = runBlocking {
        // Given
        val throwableExpected = Throwable::class.java
        every { repository.getHeadlines() } throws Throwable()

        // When
        val result = assertFails { useCase.invoke() }

        // Then
        assertEquals(throwableExpected, result::class.java)
    }
}