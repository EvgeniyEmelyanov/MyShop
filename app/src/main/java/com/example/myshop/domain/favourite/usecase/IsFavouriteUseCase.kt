package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository

class IsFavouriteUseCase(private val favouriteRepository: FavouriteRepository) {

    fun isFavourite(id: String): Boolean {
        return favouriteRepository.isFavourite(id)
    }
}