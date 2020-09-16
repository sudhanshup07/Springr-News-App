package com.s.springrnewsapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
) : Parcelable


@Parcelize
data class NewsArticle(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable


@Parcelize
data class Source(
    val id: String,
    val name: String
) : Parcelable
