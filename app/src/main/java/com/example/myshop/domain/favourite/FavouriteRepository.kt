package com.example.myshop.domain.favourite

import com.example.myshop.domain.favourite.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun getFavourite(): Favourite

    fun observeFavourite(): Flow<Favourite>

    suspend fun addToFavourite(id: String)

    suspend fun removeFavouriteItem(id: String)

    suspend fun clearFavourite()

    suspend fun isFavourite(id: String): Boolean

    suspend fun toggle(productId: String): Boolean

}