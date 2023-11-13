package com.pessoto.bbcnews.corearch.resources

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int, vararg params: String = emptyArray()): String
}