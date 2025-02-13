package com.example.mynews.ui.headlines

import com.example.mynews.data.NewsItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HeadlinesView: MvpView {
    fun showLoading(isLoading: Boolean)
    fun showHeadlines(list: List<NewsItem>)
    fun showError(message: String)
    fun showLoadingMore(isLoadingMore: Boolean)
}