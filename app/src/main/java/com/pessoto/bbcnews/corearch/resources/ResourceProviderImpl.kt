package com.pessoto.bbcnews.corearch.resources

import android.content.Context

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(resId: Int, vararg params: String): String =
        context.resources.getString(resId, *params)
}