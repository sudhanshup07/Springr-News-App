package com.s.springrnewsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.s.springrnewsapp.model.NewsSourceResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "d29d58aab88d4ea0b04ddb245a230068"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder().addInterceptor(
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface NewsSourceService {
    @GET("sources")
    fun getSourcesAsync(@Query("language") language: String,@Query("apiKey") key: String):
            Deferred<NewsSourceResponse>
}

object NewsApi {
    val retrofitService : NewsSourceService by lazy {
        retrofit.create(NewsSourceService::class.java)
    }
}