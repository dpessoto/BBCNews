package com.pessoto.bbcnews

import android.app.Application
import com.pessoto.bbcnews.corearch.data.remote.di.moduleRemote
import com.pessoto.bbcnews.corearch.resources.di.moduleResources
import com.pessoto.bbcnews.feature.listheadlines.di.moduleListHeadlines
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BbcNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BbcNewsApplication)
            modules(listOf(moduleResources, moduleRemote, moduleListHeadlines))
        }
    }
}