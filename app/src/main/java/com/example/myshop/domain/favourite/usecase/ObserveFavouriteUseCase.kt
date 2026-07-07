package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavouriteUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) {

    operator fun invoke(): Flow<Favourite> {
        return favouriteRepository.observeFavourite()
    }
}
