package com.pessoto.bbcnews.feature.article.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pessoto.bbcnews.R
import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article

internal class ArticleViewModel(private val resourceProvider: ResourceProvider) : ViewModel() {

    private val _stateView = MutableLiveData<ArticleStateView>()
    val stateView: LiveData<ArticleStateView>
        get() = _stateView

    fun setArticle(article: Article?) {
        article?.let {
            _stateView.value = ArticleStateView.DataLoaded(it)
        } ?: ArticleStateView.Error(resourceProvider.getString(R.string.no_data))
    }
}