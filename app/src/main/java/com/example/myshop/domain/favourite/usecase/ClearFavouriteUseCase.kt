package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import javax.inject.Inject
class ClearFavouriteUseCase @Inject constructor(private val favouriteRepository: FavouriteRepository) {

    suspend fun clearFavourite() {
        favouriteRepository.clearFavourite()
    }
}
