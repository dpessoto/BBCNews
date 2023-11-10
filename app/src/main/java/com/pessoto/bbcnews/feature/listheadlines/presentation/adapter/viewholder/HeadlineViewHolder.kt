package com.pessoto.bbcnews.feature.listheadlines.presentation.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pessoto.bbcnews.databinding.ListheadlinesHeadlineItemBinding
import com.pessoto.bbcnews.feature.listheadlines.domain.model.Article

internal class HeadlineViewHolder(
    private val binding: ListheadlinesHeadlineItemBinding
) : ViewHolder(binding.root) {

    fun bind(article: Article, onClickItem: (Article) -> Unit) {
        binding.root.setOnClickListener { onClickItem.invoke(article) }
        binding.headlineTextView.text = article.title
    }

    companion object {
        fun build(parent: ViewGroup): ViewHolder {
            val binding = ListheadlinesHeadlineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HeadlineViewHolder(binding)
        }
    }
}