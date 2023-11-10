package com.pessoto.bbcnews.feature.listheadlines.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article
import com.pessoto.bbcnews.feature.listheadlines.presentation.ListHeadlinesType
import com.pessoto.bbcnews.feature.listheadlines.presentation.adapter.viewholder.HeadlineViewHolder

internal class ListHeadlinesAdapter(
    private val onClickItem: (Article) -> Unit,
) : ListAdapter<Article, ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ListHeadlinesType.HEADLINE.ordinal -> HeadlineViewHolder.build(parent)
            else -> throw IllegalArgumentException("ViewHolder not defined")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is HeadlineViewHolder -> holder.bind(currentList[position], onClickItem)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}