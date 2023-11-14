package com.pessoto.bbcnews.feature.article.di

import com.pessoto.bbcnews.feature.article.presentation.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleArticle = module {

    //presentation
    viewModel { ArticleViewModel(resourceProvider = get()) }
}