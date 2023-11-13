package com.pessoto.bbcnews.corearch.resources.di

import com.pessoto.bbcnews.corearch.resources.ResourceProvider
import com.pessoto.bbcnews.corearch.resources.ResourceProviderImpl
import org.koin.dsl.module

val moduleResources = module {
    single<ResourceProvider> { ResourceProviderImpl(get()) }
}