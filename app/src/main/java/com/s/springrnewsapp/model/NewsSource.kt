package com.s.springrnewsapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsSourceResponse(
    val status: String,
    val sources:List<NewsSource>
) : Parcelable

@Parcelize
data class NewsSource(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
) : Parcelable