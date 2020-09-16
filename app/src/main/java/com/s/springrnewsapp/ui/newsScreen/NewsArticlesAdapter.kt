package com.s.springrnewsapp.ui.newsScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s.springrnewsapp.databinding.NewsArticleItemBinding
import com.s.springrnewsapp.model.NewsArticle

class NewsArticlesAdapter(private val onClickListener: OnClickListener):
    ListAdapter<NewsArticle,NewsArticlesAdapter.NewsArticlesViewHolder>(DiffCallback) {

    class NewsArticlesViewHolder(private var binding: NewsArticleItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newArticle: NewsArticle) {
            binding.newsArticle = newArticle
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsArticlesViewHolder {
        return NewsArticlesViewHolder(NewsArticleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder:NewsArticlesViewHolder, position: Int) {
        val newsArticle = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsArticle)
        }
        holder.bind(newsArticle)
    }

    class OnClickListener(val clickListener: (newsArticle:NewsArticle) -> Unit) {
        fun onClick(newsArticle: NewsArticle) = clickListener(newsArticle)
    }
}