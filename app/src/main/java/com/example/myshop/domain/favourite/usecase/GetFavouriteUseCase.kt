package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite

class GetFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    suspend fun getFavourite(): Favourite {
        return favouriteRepository.getFavourite()

    }
}