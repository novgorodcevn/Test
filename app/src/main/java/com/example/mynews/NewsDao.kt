package com.example.mynews

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.NewsItemEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsItemEntity>)

    @Query("SELECT * FROM news_items")
    suspend fun getAllNews(): List<NewsItemEntity>
}