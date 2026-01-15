package com.example.myshop

class FavouriteManager {
    private val favoriteIds = mutableSetOf<String>()

    fun isFavorite(productId: String): Boolean = productId in favoriteIds

    fun add(productId: String) {
        favoriteIds.add(productId)
    }

    fun remove(productId: String) {
        favoriteIds.remove(productId)
    }

    fun toggle(productId: String): Boolean {
        return if (favoriteIds.add(productId)) true
        else { favoriteIds.remove(productId); false }
    }

    fun getAllIds(): List<String> = favoriteIds.toList()
}
