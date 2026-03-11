package com.example.myshop.domain.favourite

import com.example.myshop.domain.favourite.model.Favourite

interface FavouriteRepository {

    fun getFavourite(): Favourite

    fun addToFavourite(id: String)

    fun removeFavouriteItem(id: String)

    fun clearFavourite()

    fun isFavourite(id: String): Boolean

    fun toggle(productId: String): Boolean

}