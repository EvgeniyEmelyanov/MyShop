package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository

class ToggleFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    fun toggle(id: String): Boolean {
        return favouriteRepository.toggle(id)

    }
}