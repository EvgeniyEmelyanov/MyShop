package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite

class AddToFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    suspend fun addToFavourite(id: String) {
        return favouriteRepository.addToFavourite(id)

    }
}