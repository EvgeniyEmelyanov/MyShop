package com.example.myshop.features.shop.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

class ShopViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(
                getAllProductsUseCase = AppGraph.getAllProductsUseCase,
                addProductUseCase = AppGraph.addProductToCartUseCase,
                moneyFormatter = AppGraph.moneyFormatter,
                imageKeyResolver = AppGraph.imageKeyResolver,
                getCartUseCase = AppGraph.getCartUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    }
}