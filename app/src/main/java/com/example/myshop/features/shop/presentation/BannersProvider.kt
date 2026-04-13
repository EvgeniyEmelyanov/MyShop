package com.example.myshop.features.shop.presentation

object BannersProvider {

    private val banners = listOf(
        BannerUiModel("Fresh Vegetables", "Get Up To 40% OFF"),
        BannerUiModel("Hot Deals", "Only Today"),
        BannerUiModel("Mega Sale", "Up to 70% OFF")
    )

    fun getBanners(): List<BannerUiModel> = banners

}