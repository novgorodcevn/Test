package com.example.mynews

import com.example.mynews.data.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country:String = "us",
        @Query("category") category: String,
        @Query("apiKey") apiKey: String

    ): Single<NewsResponse>
}