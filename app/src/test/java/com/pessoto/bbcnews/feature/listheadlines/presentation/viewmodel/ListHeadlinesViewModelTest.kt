package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.domain.exception.EmptyArticleListException
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.domain.usecase.GetHeadLinesUseCase
import com.pessoto.bbcnews.feature.listheadlines.presentation.model.ListHeadlineError
import com.pessoto.bbcnews.feature.listheadlines.util.mockSortedArticles
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ListHeadlinesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getHeadLinesUseCase = mockk<GetHeadLinesUseCase>()
    private val resource = mockk<ResourceProvider>()

    @Test
    fun `getHeadline should emit DataLoaded state on success`() = runBlocking {
        // Given
        val mockArticles = mockSortedArticles()
        coEvery { getHeadLinesUseCase.invoke() } returns flow { emit(mockArticles) }

        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase,resource, coroutineTestRule.testDispatcher)

        // When
        viewModel.getHeadline()

        // Then
        assertEquals(ListHeadlinesStateView.DataLoaded(mockArticles), viewModel.stateView.value)
    }

    @Test
    fun `getHeadline should emit Error state on failure is EmptyArticleListException`() = runBlocking {
        // Given
        val mockException = EmptyArticleListException("Test exception")
        coEvery { getHeadLinesUseCase.invoke() } returns flow { throw mockException }
        every { resource.getString(R.string.update) } returns "Update"

        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase,resource, coroutineTestRule.testDispatcher)

        // When
        viewModel.getHeadline()

        // Then
        assertEquals(ListHeadlinesStateView.Error(ListHeadlineError(
            buttonDescription = "Update",
            messageDescription = "Test exception"
        )), viewModel.stateView.value)
    }

    @Test
    fun `getHeadline should emit Error state on failure is Generic Error`() = runBlocking {
        // Given
        val mockException = Throwable("Test exception")
        coEvery { getHeadLinesUseCase.invoke() } returns flow { throw mockException }
        every { resource.getString(R.string.try_again) } returns "Update"
        every { resource.getString(R.string.generic_error) } returns "Test exception"

        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase,resource, coroutineTestRule.testDispatcher)

        // When
        viewModel.getHeadline()

        // Then
        assertEquals(ListHeadlinesStateView.Error(ListHeadlineError(
            buttonDescription = "Update",
            messageDescription = "Test exception"
        )), viewModel.stateView.value)
    }

    @Test
    fun `handleClickItem should set GoToArticle state`() {
        // Given
        val mockArticle = mockk<Article>()
        val viewModel = ListHeadlinesViewModel(getHeadLinesUseCase,resource, coroutineTestRule.testDispatcher)

        // When
        viewModel.handleClickItem(mockArticle)

        // Then
        assertEquals(ListHeadlinesStateView.GoToArticle(mockArticle), viewModel.stateView.value)
    }
}
