package com.s.springrnewsapp.ui.newsChannelsScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s.springrnewsapp.databinding.NewsChannelItemBinding
import com.s.springrnewsapp.model.NewsSource

class NewsChannelsAdapter(private val onClickListener: OnClickListener):
    ListAdapter<NewsSource,NewsChannelsAdapter.NewsSourceViewHolder>(DiffCallback) {

    class NewsSourceViewHolder(private var binding: NewsChannelItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newSource: NewsSource) {
            binding.newSource = newSource
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsSource>() {
        override fun areItemsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsSourceViewHolder {
        return NewsSourceViewHolder(NewsChannelItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder:NewsSourceViewHolder, position: Int) {
        val newsSource = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsSource)
        }
        holder.bind(newsSource)
    }

    class OnClickListener(val clickListener: (newsSource:NewsSource) -> Unit) {
        fun onClick(newsSource: NewsSource) = clickListener(newsSource)
    }
}