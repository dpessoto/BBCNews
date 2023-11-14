package com.pessoto.bbcnews.feature.listheadlines.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Source(
    val id: String?,
    val name: String
) :Parcelable