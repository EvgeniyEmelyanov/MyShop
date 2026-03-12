package com.example.myshop.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

class CartViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(
                getProductByIdUseCase = AppGraph.getProductByIdUseCase,
                getCartUseCase = AppGraph.getCartUseCase,
                addProductToCartUseCase = AppGraph.addProductToCartUseCase,
                setAmountUseCase = AppGraph.setAmountUseCase,
                removeProductUseCase = AppGraph.removeProductUseCase,
                clearProductsUseCase = AppGraph.clearProductsUseCase,
                increaseAmountUseCase = AppGraph.increaseAmountUseCase,
                decreaseAmountUseCase = AppGraph.decreaseAmountUseCase,
                imageKeyResolver = AppGraph.imageKeyResolver,
                quantityFormatter = AppGraph.quantityFormatter,
                calculateCartTotalsUseCase = AppGraph.calculateCartTotalsUseCase,
                moneyFormatter = AppGraph.moneyFormatter,
                linePriceCalculator = AppGraph.linePriceCalculator
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}