package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.domain.exception.EmptyArticleListException
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.domain.usecase.GetHeadLinesUseCase
import com.pessoto.bbcnews.feature.listheadlines.presentation.model.ListHeadlineError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class ListHeadlinesViewModel(
    private val getHeadLinesUseCase: GetHeadLinesUseCase,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _stateView = MutableLiveData<ListHeadlinesStateView>()
    val stateView: LiveData<ListHeadlinesStateView>
        get() = _stateView

    fun getHeadline() {
        viewModelScope.launch {
            getHeadLinesUseCase.invoke()
                .flowOn(dispatcher)
                .onStart { _stateView.value = ListHeadlinesStateView.Loading }
                .catch { handleError(it) }
                .collect { articles ->
                    _stateView.value = ListHeadlinesStateView.DataLoaded(articles)
                }
        }
    }

    private fun handleError(it: Throwable) {
        _stateView.value = when (it) {
            is EmptyArticleListException -> ListHeadlinesStateView.Error(
                ListHeadlineError(
                    buttonDescription = resourceProvider.getString(
                        R.string.update
                    ), messageDescription = it.message.orEmpty()
                )
            )
            else -> ListHeadlinesStateView.Error(
                ListHeadlineError(
                    buttonDescription = resourceProvider.getString(R.string.try_again),
                    messageDescription = resourceProvider.getString(R.string.generic_error)
                )
            )
        }
    }

    fun handleClickItem(article: Article) {
        _stateView.value = ListHeadlinesStateView.GoToArticle(article)
    }
}
