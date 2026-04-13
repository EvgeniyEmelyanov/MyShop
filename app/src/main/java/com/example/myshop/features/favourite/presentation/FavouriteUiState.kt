package com.example.myshop.features.favourite.presentation

data class FavouriteUiState(
    val items: List<FavouriteUiModel> = emptyList(),
    val isLoading: Boolean = false
)
