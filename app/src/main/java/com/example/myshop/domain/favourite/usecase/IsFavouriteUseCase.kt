package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import javax.inject.Inject
class IsFavouriteUseCase @Inject constructor(private val favouriteRepository: FavouriteRepository) {

    suspend fun isFavourite(id: String): Boolean {
        return favouriteRepository.isFavourite(id)
    }
}
