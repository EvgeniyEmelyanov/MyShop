package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository

class RemoveFromFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    suspend fun removeFromFavourite(id: String) {
        favouriteRepository.removeFavouriteItem(id)
    }
}