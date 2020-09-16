package com.s.springrnewsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.s.springrnewsapp.model.NewsArticleResponse
import com.s.springrnewsapp.model.NewsSourceResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "d29d58aab88d4ea0b04ddb245a230068"

//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()


private val okHttpClient = OkHttpClient.Builder().addInterceptor(
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface NewsSourceService {
    @GET("sources")
    fun getSourcesAsync(@Query("language") language: String,@Query("apiKey") key: String):
            Deferred<NewsSourceResponse>

    @GET("everything")
    fun getNewsArticlesAsync(@Query("sources") sourceId: String,@Query("apiKey") key: String):
            Deferred<NewsArticleResponse>
}

object NewsApi {
    val retrofitService : NewsSourceService by lazy {
        retrofit.create(NewsSourceService::class.java)
    }
}