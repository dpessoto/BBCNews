package com.pessoto.bbcnews.feature.article.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pessoto.bbcnews.corearch.presentation.extensions.gone
import com.pessoto.bbcnews.corearch.presentation.extensions.loadImage
import com.pessoto.bbcnews.corearch.presentation.extensions.visible
import com.pessoto.bbcnews.databinding.ActivityArticleBinding
import com.pessoto.bbcnews.feature.article.presentation.viewmodel.ArticleStateView
import com.pessoto.bbcnews.feature.article.presentation.viewmodel.ArticleViewModel
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private val viewModel: ArticleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserve()

        val article = intent.getParcelableExtra<Article>(ARTICLE_EXTRA)
        viewModel.setArticle(article)
    }

    private fun setupObserve() {
        viewModel.stateView.observe(this) { stateView ->
            when (stateView) {
                is ArticleStateView.DataLoaded -> stateDataLoaded(stateView.data)
                is ArticleStateView.Error -> stateError(stateView.message)
            }
        }
    }

    private fun stateDataLoaded(article: Article) = with(binding) {
        articleScrollView.visible()
        articleErrorTextView.gone()
        articleImageView.loadImage(article.urlToImage)
        articleTitleTextView.text = article.title
        articleDescriptionTextView.text = article.description
        articleContentTextView.text = article.content
    }

    private fun stateError(message: String) = with(binding) {
        articleScrollView.gone()
        articleErrorTextView.visible()
        articleErrorTextView.text = message
    }
}