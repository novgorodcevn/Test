package com.example.mynews

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mynews.data.NewsItem
import com.example.mynews.data.NewsItemEntity
import com.example.mynews.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl @RequiresApi(Build.VERSION_CODES.O) constructor(
    @SuppressLint("NewApi") private val apiService: NewsApiService =RetrofitBuilder.buildApiService(),
    private val database : AppDatabase
) : NewsRepository {

    override suspend fun getTopHeadlines(): List<NewsItem> = withContext(Dispatchers.IO) {
        val newsFromDb = database.newsDao().getAllNews()

        if(newsFromDb.isNotEmpty()) {
            return@withContext newsFromDb.map {
                NewsItem(
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt,
                    com.example.mynews.data.Source(it.sourceId, it.sourceName),
                    it.author,
                    it.content
                )
            }
        }

        val newsFromApi = apiService.getTopHeadlines().articles

        database.newsDao().insertAll(newsFromApi.map {
            NewsItemEntity(
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                sourceId = it.source.id,
                sourceName = it.source.name,
                author = it.author,
                content = it.content
            )
        })

        return@withContext newsFromApi
    }
}