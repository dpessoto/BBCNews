package com.pessoto.bbcnews.corearch.data.remote.mapper

interface Mapper<S, T> {
    fun map(source: S): T
}
