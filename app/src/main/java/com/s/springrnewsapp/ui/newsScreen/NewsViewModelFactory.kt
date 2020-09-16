package com.s.springrnewsapp.ui.newsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(private val sourceId: String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(sourceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}