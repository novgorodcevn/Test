package com.example.mynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynews.NewsDao
import com.example.mynews.data.NewsItem

class ViewModelFactory(private val newsItemDao: NewsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
            return HeadlinesViewModel(newsItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
