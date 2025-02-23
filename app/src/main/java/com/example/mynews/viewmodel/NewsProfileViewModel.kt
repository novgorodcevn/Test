package com.example.mynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.data.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<NewsProfileUiState>(NewsProfileUiState.Loading)
    val uiState: StateFlow<NewsProfileUiState> = _uiState

    private var isFavorite: Boolean = false

    fun loadNews(article: Article) {
        viewModelScope.launch {
            try {
                kotlinx.coroutines.delay(500)

                _uiState.value = NewsProfileUiState.Success(isFavorite)
            } catch (e: Exception) {

                _uiState.value = NewsProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite() {
        isFavorite = !isFavorite
        _uiState.value = NewsProfileUiState.Success(isFavorite)
    }
}
