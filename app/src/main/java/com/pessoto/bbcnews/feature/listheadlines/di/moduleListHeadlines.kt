package com.pessoto.bbcnews.feature.listheadlines.di

import com.pessoto.bbcnews.feature.listheadlines.data.api.ListHeadlinesService
import com.pessoto.bbcnews.feature.listheadlines.data.mappers.NewsMapper
import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepository
import com.pessoto.bbcnews.feature.listheadlines.data.repository.ListHeadlinesRepositoryImpl
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSource
import com.pessoto.bbcnews.feature.listheadlines.data.source.remote.ListHeadlinesRemoteDataSourceImpl
import com.pessoto.bbcnews.feature.listheadlines.domain.usecase.GetHeadLinesUseCase
import org.koin.dsl.module
import retrofit2.Retrofit

val moduleListHeadlines = module {
    //data
    factory { get<Retrofit>().create(ListHeadlinesService::class.java) }
    factory<ListHeadlinesRemoteDataSource> { ListHeadlinesRemoteDataSourceImpl(service = get()) }
    factory<ListHeadlinesRepository> {
        ListHeadlinesRepositoryImpl(
            remoteDataSource = get(),
            mapper = NewsMapper()
        )
    }

    //domain
    factory { GetHeadLinesUseCase(repository = get()) }
}