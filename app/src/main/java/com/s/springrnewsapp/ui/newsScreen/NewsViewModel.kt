package com.s.springrnewsapp.ui.newsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.s.springrnewsapp.model.NewsArticle
import com.s.springrnewsapp.model.NewsSource
import com.s.springrnewsapp.network.API_KEY
import com.s.springrnewsapp.network.NewsApi
import com.s.springrnewsapp.ui.newsChannelsScreen.NewsApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(val sourceId:String):ViewModel() {

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _newsArticles = MutableLiveData<List<NewsArticle>>()

    val newsArticles: LiveData<List<NewsArticle>>
        get() = _newsArticles


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getNewsArticles()
    }

    private fun getNewsArticles() {
        coroutineScope.launch {

            _status.value = NewsApiStatus.LOADING

            val getNewsArticleDeferred = NewsApi.retrofitService.getNewsArticlesAsync(sourceId, API_KEY)
            try {
                val result = getNewsArticleDeferred.await()
                _newsArticles.value = result.articles

                _status.value = NewsApiStatus.DONE
            } catch (e: Exception){
                _status.value = NewsApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val _navigateToSelectedNewsArticle = MutableLiveData<NewsArticle?>()

    // The external immutable LiveData for the navigation
    val navigateToSelectedNewsArticle: LiveData<NewsArticle?>
        get() = _navigateToSelectedNewsArticle

    fun displayNewsArticleDetails(newsArticle: NewsArticle) {
        _navigateToSelectedNewsArticle.value = newsArticle
    }

    fun displayNewsArticleDetailsComplete() {
        _navigateToSelectedNewsArticle.value = null
    }
}