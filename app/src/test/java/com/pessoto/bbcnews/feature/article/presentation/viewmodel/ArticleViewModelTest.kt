package com.pessoto.bbcnews.feature.article.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel.CoroutineTestRule
import com.pessoto.bbcnews.feature.listheadlines.util.mockSortedArticles
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ArticleViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val resource = mockk<ResourceProvider>()

    @Test
    fun `setArticle Should emit DataLoaded state When article is not null`() {
        // Given
        val viewModel = ArticleViewModel(resource)
        val article = mockSortedArticles()[0]

        // When
        viewModel.setArticle(article)

        // Then
        val state = viewModel.stateView.value
        assertTrue(state is ArticleStateView.DataLoaded)
        assertEquals(ArticleStateView.DataLoaded(article), viewModel.stateView.value)
    }

    @Test
    fun `setArticle Should emit Error state When article is null`() {
        // Given
        every { resource.getString(R.string.no_data) } returns "error"
        val viewModel = ArticleViewModel(resource)

        // When
        viewModel.setArticle(null)

        // Then
        val state = viewModel.stateView.value
        assertTrue(state is ArticleStateView.Error)
        assertEquals(ArticleStateView.Error("error"), viewModel.stateView.value)
    }
}