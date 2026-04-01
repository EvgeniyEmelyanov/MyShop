package com.example.myshop.features.productsByCategory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

@Suppress("UNCHECKED_CAST")
class ProductsByCategoryViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsByCategoryViewModel::class.java)) {
            return ProductsByCategoryViewModel(
                getCartUseCase = AppGraph.getCartUseCase,
                addProductToCartUseCase = AppGraph.addProductToCartUseCase,
                getProductsByCategoryUseCase = AppGraph.getProductsByCategoryUseCase,
                moneyFormatter = AppGraph.moneyFormatter,
                imageKeyResolver = AppGraph.imageKeyResolver,
                getProductByIdUseCase = AppGraph.getProductByIdUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}