package com.example.myshop.features.shop.model

import com.example.myshop.core.ui.CommonProductUiModel

data class ShopUiState(
    val banners: List<BannerUiModel> = emptyList(),
    val exclusiveOffers: List<CommonProductUiModel> = emptyList(),
    val bestSelling: List<CommonProductUiModel> = emptyList(),
    val groceriesProducts: List<CommonProductUiModel> = emptyList(),
    val groceriesCategories: List<GroceriesCategoryUiModel> = emptyList()
    )
