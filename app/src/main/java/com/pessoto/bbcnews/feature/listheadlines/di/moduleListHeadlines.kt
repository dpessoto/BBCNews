package com.pessoto.bbcnews.feature.listheadlines.di

import com.pessoto.bbcnews.feature.listheadlines.data.api.ListHeadlinesService
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSourceImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val moduleListHeadlines = module {
    factory { get<Retrofit>().create(ListHeadlinesService::class.java) }
    factory<ListHeadlinesRemoteDataSource> { ListHeadlinesRemoteDataSourceImpl(service = get()) }
}