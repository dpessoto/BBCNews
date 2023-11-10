package com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pessoto.bbcnews.feature.listheadlines.domain.usecase.GetHeadLinesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class ListHeadlinesViewModel(
    private val getHeadLinesUseCase: GetHeadLinesUseCase,
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
                .catch { _stateView.value = ListHeadlinesStateView.Error(it) }
                .collect { _stateView.value = ListHeadlinesStateView.DataLoaded(it) }
        }
    }
}
