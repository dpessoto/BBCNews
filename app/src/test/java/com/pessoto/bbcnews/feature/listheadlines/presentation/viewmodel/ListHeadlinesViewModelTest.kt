package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pessoto.bbcnews.feature.listheadlines.domain.usecase.GetHeadLinesUseCase
import com.pessoto.bbcnews.feature.listheadlines.util.mockNews
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ListHeadlinesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getHeadLinesUseCase = mockk<GetHeadLinesUseCase>()

    @Test
    fun `getHeadline should emit DataLoaded state on success`() = runBlocking {
        // Given
        val mockNews = mockNews()
        coEvery { getHeadLinesUseCase.invoke() } returns flow { emit(mockNews) }

        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase, coroutineTestRule.testDispatcher)

        // When
        viewModel.getHeadline()

        // Then
        assertEquals(ListHeadlinesStateView.DataLoaded(mockNews), viewModel.stateView.value)
    }


    @Test
    fun `getHeadline should emit Error state on failure`() = runBlocking {
        // Given
        val mockException = Throwable("Test exception")
        coEvery { getHeadLinesUseCase.invoke() } returns flow { throw mockException }

        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase, coroutineTestRule.testDispatcher)

        // When
        viewModel.getHeadline()

        // Then
        val expectedErrorState = ListHeadlinesStateView.Error(mockException)
        val actualErrorState = viewModel.stateView.value

        assertTrue(actualErrorState is ListHeadlinesStateView.Error)
        assertEquals(expectedErrorState.e.message, (actualErrorState as ListHeadlinesStateView.Error).e.message)
    }
}