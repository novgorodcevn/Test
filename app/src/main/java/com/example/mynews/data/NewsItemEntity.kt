package com.example.mynews.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "news_items")
data class NewsItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary Key
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: OffsetDateTime,
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val content: String?
)