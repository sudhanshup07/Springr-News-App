package com.s.springrnewsapp.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.s.springrnewsapp.model.NewsSource
import com.s.springrnewsapp.ui.newsChannelsScreen.NewsChannelsAdapter

@BindingAdapter("listChannelData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NewsSource>?) {
    val adapter = recyclerView.adapter as NewsChannelsAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("title")
fun bindText(textView: TextView, title:String?){
    textView.text = title
}