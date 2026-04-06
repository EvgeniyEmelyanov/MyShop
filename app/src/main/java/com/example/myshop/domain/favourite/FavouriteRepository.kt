package com.example.myshop.domain.favourite

import com.example.myshop.domain.favourite.model.Favourite

interface FavouriteRepository {

    suspend fun getFavourite(): Favourite

    suspend fun addToFavourite(id: String)

    suspend fun removeFavouriteItem(id: String)

    suspend fun clearFavourite()

    suspend fun isFavourite(id: String): Boolean

    suspend fun toggle(productId: String): Boolean

}