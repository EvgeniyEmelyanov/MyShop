package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository

class ClearFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    suspend fun clearFavourite() {
        favouriteRepository.clearFavourite()
    }
}