package com.example.mynews.viewmodel

import com.example.mynews.NewsApiService
import com.example.mynews.NewsDao
import com.example.mynews.data.Article
import com.example.mynews.data.NewsItem
import io.reactivex.rxjava3.core.Single

class HeadlinesModel(private val itemDao: NewsDao,private val newsApiService:NewsApiService) {

   suspend fun getHeadlines(page: Int,category:String): List<NewsItem> {
       try {
           val response = newsApiService.getTopHeadlines(category = category)
           val news = response.articles.map { articleToNewsItem(it, category) }
           itemDao.insertAll(news)
           return news
       } catch (e: Exception) {
           return itemDao.getNewsByCategory(category,page,10)
       }
   }
    private fun articleToNewsItem(article: Article, category: String): NewsItem{
        return NewsItem(
            title = article.title,
            description = article.description ?: "",
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            category = category,
            sourceId = article.source.id,
            sourceName = article.source.name,
            author = article.author,
            content = article.content,
            isSaved = false
        )
    }
}