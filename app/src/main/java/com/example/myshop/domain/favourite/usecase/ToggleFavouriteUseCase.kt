package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import javax.inject.Inject
class ToggleFavouriteUseCase @Inject constructor(private val favouriteRepository: FavouriteRepository) {

    suspend fun toggle(id: String): Boolean {
        return favouriteRepository.toggle(id)

    }
}
