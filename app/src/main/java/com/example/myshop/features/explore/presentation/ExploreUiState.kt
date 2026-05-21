package com.example.myshop.features.explore.presentation

import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.ui.CommonProductUiModel

data class ExploreUiState(
    val categories: List<ExploreCategoryUiModel> = emptyList(),
    val products: List<CommonProductUiModel> = emptyList(),
    val searchQuery: String = "",
    val filterParams: FilterParams = FilterParams(),
    val isLoading: Boolean = false
) {

    val isSearchMode: Boolean
        get() = searchQuery.isNotBlank()
}