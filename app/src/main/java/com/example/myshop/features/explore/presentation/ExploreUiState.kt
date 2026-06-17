package com.example.myshop.features.explore.presentation

import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState

data class ExploreUiState(
    val categories: List<ExploreCategoryUiModel> = emptyList(),
    val products: List<CommonProductUiModel> = emptyList(),
    val searchQuery: String = "",
    val filterParams: FilterParams = FilterParams(),
    val contentState: ContentState = ContentState.LOADING
) {

    val isSearchMode: Boolean
        get() = searchQuery.isNotBlank()
}
