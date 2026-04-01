package com.example.myshop.features.productsByCategory.presentation

import com.example.myshop.core.ui.CommonProductUiModel

data class ProductsByCategoryUiState(
    val products: List<CommonProductUiModel> = emptyList(),
    val isLoading: Boolean = false
)