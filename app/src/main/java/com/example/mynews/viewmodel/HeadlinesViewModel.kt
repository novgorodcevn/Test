package com.example.mynews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.NewsDao
import com.example.mynews.data.NewsItem

class HeadlinesViewModel(private val itemDao:NewsDao) : ViewModel() {
    private val _itemList = MutableLiveData<List<NewsItem>>()
    val itemList: LiveData<List<NewsItem>> = _itemList


}

