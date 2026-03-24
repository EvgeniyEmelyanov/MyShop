package com.example.myshop.features.productsByCategory.model

import com.example.myshop.core.ui.CommonProductUiModel

data class ProductsByCategoryUiState(
    val products: List<CommonProductUiModel> = emptyList()
)