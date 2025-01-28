package com.example.mynews

import com.example.mynews.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country:String = "us",
        @Query("apiKey") apiKey: String = "8e3d4af8b176486cad2ea977f8a2d01c"

    ): NewsResponse
}