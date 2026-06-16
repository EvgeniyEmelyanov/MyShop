package com.example.myshop.features.favourite.presentation

import com.example.myshop.core.ui.ContentState

data class FavouriteUiState(
    val items: List<FavouriteUiModel> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
)
