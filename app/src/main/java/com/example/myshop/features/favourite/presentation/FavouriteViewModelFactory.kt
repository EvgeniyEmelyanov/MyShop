package com.example.myshop.features.favourite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.di.AppGraph

class FavouriteViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(
                getProductByIdUseCase = AppGraph.getProductByIdUseCase,
                getFavouriteUseCase = AppGraph.getFavouriteUseCase,
                addToFavouriteUseCase = AppGraph.addToFavouriteUseCase,
                removeFromFavouriteUseCase = AppGraph.removeFromFavouriteUseCase,
                clearFavouriteUseCase = AppGraph.clearFavouriteUseCase,
                isFavouriteUseCase = AppGraph.isFavouriteUseCase,
                toggleFavouriteUseCase = AppGraph.toggleFavouriteUseCase,
                imageKeyResolver = AppGraph.imageKeyResolver,
                moneyFormatter = AppGraph.moneyFormatter
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}