package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import javax.inject.Inject
class RemoveFromFavouriteUseCase @Inject constructor(private val favouriteRepository: FavouriteRepository) {

    suspend fun removeFromFavourite(id: String) {
        favouriteRepository.removeFavouriteItem(id)
    }
}
