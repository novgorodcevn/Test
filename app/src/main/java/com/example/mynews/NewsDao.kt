package com.example.mynews

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynews.data.NewsItem
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsItem>)

  //  @Query("SELECT * FROM news_items")
  //  fun getAllItems(): List<NewsItem>

    @Query("SELECT id, title, description, url, urlToImage, publishedAt, category, sourceId, sourceName, author, content, isSaved FROM news_items WHERE category = :category LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun getNewsByCategory(category: String, page: Int, pageSize: Int): List<NewsItem>

    @Query("SELECT  id, title, description, url, urlToImage, publishedAt, category, sourceId, sourceName, author, content, isSaved FROM news_items ")
    suspend fun getAllNews(): List<NewsItem>

    @Query("SELECT  id, title, description, url, urlToImage, publishedAt, category, sourceId, sourceName, author, content, isSaved FROM news_items LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    suspend fun getAllNews(page: Int, pageSize: Int): List<NewsItem>

}