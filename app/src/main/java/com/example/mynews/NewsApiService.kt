package com.example.mynews

import com.example.mynews.data.NewsResponse
import com.example.mynews.data.SourcesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>
    @GET("sources")
    fun getSources(
        @Query("apiKey") apiKey: String
    ):Single<SourcesResponse>
    @GET("top-headlines")
    fun getTopHeadlinesBySource(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>
}