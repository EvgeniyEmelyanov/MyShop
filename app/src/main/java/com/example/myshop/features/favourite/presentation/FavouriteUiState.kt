package com.example.myshop

import com.example.myshop.features.favourite.presentation.FavouriteUiModel

data class FavouriteUiState(
    val items: List<FavouriteUiModel> = emptyList()
)
