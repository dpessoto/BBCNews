package com.pessoto.bbcnews.feature.listheadlines.domain.usecase

import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepository
import com.pessoto.bbcnews.feature.listheadlines.domain.model.News
import com.pessoto.bbcnews.feature.listheadlines.util.mockNews
import com.pessoto.bbcnews.feature.listheadlines.util.mockNewsSorted
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.ExperimentalTime


@ExperimentalTime
internal class GetHeadLinesUseCaseTest {

    private val repository = mockk<ListHeadlinesRepository>()
    private val useCase = GetHeadLinesUseCase(repository)

    @Test
    fun `getHeadLinesUseCase Should return News Sorted by date When repository is success`() = runBlocking {
            // Given
            val unsortedNews = mockNews()
            val sortedNews = mockNewsSorted()
            every { repository.getHeadlines() } returns flowOf(unsortedNews)

            // When
            var news: News? = null
            useCase.invoke().collect {
                news = it
            }

            // Then
            assertEquals(sortedNews, news)
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