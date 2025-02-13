package com.example.mynews.data

data class NewsResponse(
    val status: String,
    val totalResults:Int,
    val articles: List<Article>
)
