package com.example.mynews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.NewsApiService
import com.example.mynews.NewsDao
import com.example.mynews.data.NewsItem
import com.example.mynews.data.NewsRepository
import kotlinx.coroutines.launch

class HeadlinesViewModel(private val itemDao:NewsDao) : ViewModel() {
    private val _itemList = MutableLiveData<List<NewsItem>>()
    val itemList: LiveData<List<NewsItem>> = _itemList

 //   init {
   //     loadItems()
    }

  //  private fun loadItems() {
   //     viewModelScope.launch {
    //        _itemList.value = itemDao.getAllItems()
   //     }
  //  }
//}