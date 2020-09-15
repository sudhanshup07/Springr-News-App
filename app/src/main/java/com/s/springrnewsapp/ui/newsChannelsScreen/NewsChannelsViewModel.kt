package com.s.springrnewsapp.ui.newsChannelsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.s.springrnewsapp.model.NewsSource
import com.s.springrnewsapp.network.API_KEY
import com.s.springrnewsapp.network.NewsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class NewsApiStatus{ LOADING, ERROR, DONE }

class NewsChannelsViewModel:ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<NewsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _newsSources = MutableLiveData<List<NewsSource>>()

    val newsSources: LiveData<List<NewsSource>>
        get() = _newsSources


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getNewsSources()
    }

    private fun getNewsSources() {
        coroutineScope.launch {

            val getNewsChannelsDeferred = NewsApi.retrofitService.getSourcesAsync("en", API_KEY)
            try {
                val result = getNewsChannelsDeferred.await()
                _newsSources.value = result.sources
            } catch (e: Exception){

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSelectedNewsSource = MutableLiveData<NewsSource?>()

    // The external immutable LiveData for the navigation
    val navigateToSelectedNewsSource: LiveData<NewsSource?>
        get() = _navigateToSelectedNewsSource

    fun displayNewsSourceDetails(newsSource: NewsSource) {
        _navigateToSelectedNewsSource.value = newsSource
    }

    // After the navigation has taken place, make sure navigateToSelectedProperty is set to null

    fun displayNewsSourceDetailsComplete() {
        _navigateToSelectedNewsSource.value = null
    }

}