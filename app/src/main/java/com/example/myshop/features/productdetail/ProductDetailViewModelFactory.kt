package com.example.myshop.features.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

class ProductDetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(
                getProductByIdUseCase = AppGraph.getProductByIdUseCase,
                getCartUseCase = AppGraph.getCartUseCase,
                addProductUseCase = AppGraph.addProductUseCase,
                setAmountUseCase = AppGraph.setAmountUseCase,
                increaseAmountUseCase = AppGraph.increaseAmountUseCase,
                decreaseAmountUseCase = AppGraph.decreaseAmountUseCase,
                calculateCartTotalsUseCase = AppGraph.calculateCartTotalsUseCase,
                quantityFormatter = AppGraph.quantityFormatter,
                moneyFormatter = AppGraph.moneyFormatter,
                linePriceCalculator = AppGraph.linePriceCalculator,
                imageKeyResolver = AppGraph.imageKeyResolver,
                isFavouriteUseCase = AppGraph.isFavouriteUseCase,
                toggleFavouriteUseCase = AppGraph.toggleFavouriteUseCase
            ) as T
        }
        error("Unknown VM: ${modelClass.name}")
    }
}