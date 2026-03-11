package com.example.myshop.data.favourite

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import com.example.myshop.domain.favourite.model.FavouriteItem

class FavouriteRepositoryImpl : FavouriteRepository {

    private val favouriteList = mutableSetOf<String>()


    override fun getFavourite(): Favourite {
        val items = favouriteList.map { productId ->
            FavouriteItem(productId)
        }
        return Favourite(items)
    }

    override fun isFavourite(id: String): Boolean {
        return id in favouriteList
    }

    override fun clearFavourite() {
        favouriteList.clear()
    }

    override fun removeFavouriteItem(id: String) {
        favouriteList.remove(id)
    }

    override fun addToFavourite(id: String) {
        favouriteList.add(id)
    }

    override fun toggle(productId: String): Boolean {
        return if (favouriteList.add(productId)) true
        else {
            favouriteList.remove(productId); false
        }
    }

}