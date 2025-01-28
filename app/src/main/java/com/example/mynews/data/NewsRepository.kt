package com.example.mynews.data

interface NewsRepository {
    suspend fun getTopHeadlines():List<NewsItem>
}