package com.pessoto.bbcnews

import android.app.Application
import com.pessoto.bbcnews.data.remote.moduleRemote
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BbcNewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BbcNewsApplication)
            modules(moduleRemote)
        }
    }
}