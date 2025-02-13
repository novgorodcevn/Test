package com.example.mynews.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_items")
data class NewsItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Тип изменен на Int
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String, // String, как в API
    val category: String,
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val content: String?,
    var isSaved: Boolean = false
)
