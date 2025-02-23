package com.example.mynews.viewmodel

sealed class NewsProfileUiState {
    object Loading : NewsProfileUiState()
    data class Success(val isFavorite: Boolean) : NewsProfileUiState()
    data class Error(val message: String) : NewsProfileUiState()
}