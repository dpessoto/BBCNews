package com.pessoto.bbcnews.feature.listheadlines.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pessoto.bbcnews.corearch.presentation.extensions.gone
import com.pessoto.bbcnews.corearch.presentation.extensions.visible
import com.pessoto.bbcnews.databinding.ActivityListHeadlinesBinding
import com.pessoto.bbcnews.feature.article.presentation.ARTICLE_EXTRA
import com.pessoto.bbcnews.feature.article.presentation.ArticleActivity
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.presentation.adapter.ListHeadlinesAdapter
import com.pessoto.bbcnews.feature.listheadlines.presentation.model.ListHeadlineError
import com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel.ListHeadlinesStateView
import com.pessoto.bbcnews.feature.listheadlines.presentation.viewmodel.ListHeadlinesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListHeadlinesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListHeadlinesBinding
    private val viewModel: ListHeadlinesViewModel by viewModel()
    private val adapter by lazy {
        ListHeadlinesAdapter (viewModel::handleClickItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClick()
        setupObserve()

        viewModel.getHeadline()
    }

    private fun setupObserve() {
        viewModel.stateView.observe(this) { stateView ->
            when (stateView) {
                is ListHeadlinesStateView.Loading -> stateLoading()
                is ListHeadlinesStateView.DataLoaded -> stateDataLoaded(stateView.data)
                is ListHeadlinesStateView.Error -> stateError(stateView.error)
                is ListHeadlinesStateView.GoToArticle -> goToArticle(stateView)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.headlineRecyclerView.adapter = adapter
    }

    private fun setupClick() {
        binding.headlineErrorButton.setOnClickListener {
            viewModel.getHeadline()
        }
    }

    private fun stateLoading() = with(binding) {
        headlineRecyclerView.gone()
        headlineErrorButton.gone()
        headlineErrorTextView.gone()
        headlineProgressBar.visible()
    }

    private fun stateDataLoaded(data: List<Article>) = with(binding) {
        headlineErrorButton.gone()
        headlineErrorTextView.gone()
        headlineProgressBar.gone()
        headlineRecyclerView.visible()
        adapter.submitList(data)
    }

    private fun stateError(error: ListHeadlineError) = with(binding) {
        headlineRecyclerView.gone()
        headlineProgressBar.gone()
        headlineErrorButton.visible()
        headlineErrorTextView.visible()
        headlineErrorTextView.text = error.messageDescription
        headlineErrorButton.text = error.buttonDescription
    }

    private fun goToArticle(stateView: ListHeadlinesStateView.GoToArticle) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(ARTICLE_EXTRA, stateView.article)
        startActivity(intent)
    }

    override fun onDestroy() {
        binding.headlineRecyclerView.adapter = null
        super.onDestroy()
    }
}
