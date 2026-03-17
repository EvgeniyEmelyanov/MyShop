package com.example.myshop.features.shop.model

data class ShopUiState(
    val banners: List<BannerUiModel> = emptyList(),
    val exclusiveOffers: List<ProductCardUiModel> = emptyList(),
    val bestSelling: List<ProductCardUiModel> = emptyList(),
    val groceriesProducts: List<ProductCardUiModel> = emptyList(),
    val groceriesCategories: List<GroceriesCategoryUiModel> = emptyList()
    )
