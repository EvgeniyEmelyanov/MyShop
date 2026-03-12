package com.example.myshop.features.shop.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

class ShopViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(
                getProductByIdUseCase = AppGraph.getProductByIdUseCase,
                getCartUseCase = AppGraph.getCartUseCase,
                addProductToCartUseCase = AppGraph.addProductToCartUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    }
}