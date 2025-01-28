package com.example.mynews.data

import java.time.OffsetDateTime

data class NewsItem(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: OffsetDateTime,
    val source: Source,
    val author: String?,
    val content: String?
)