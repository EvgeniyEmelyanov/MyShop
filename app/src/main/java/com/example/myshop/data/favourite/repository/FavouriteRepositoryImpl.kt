package com.example.myshop.data.favourite.repository

import com.example.myshop.data.favourite.local.dao.FavouriteDao
import com.example.myshop.data.favourite.local.mapper.toDomain
import com.example.myshop.data.favourite.local.mapper.toEntity
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(private val favouriteDao: FavouriteDao) :
    FavouriteRepository {


    override suspend fun getFavourite(): Favourite {
        val items = favouriteDao.getAll().map { entity ->
            entity.toDomain()
        }
        return Favourite(items)
    }

    override suspend fun isFavourite(id: String): Boolean {
        return favouriteDao.isFavourite(id)
    }

    override suspend fun clearFavourite() {
        favouriteDao.clear()
    }

    override suspend fun removeFavouriteItem(id: String) {
        favouriteDao.remove(id)
    }

    override suspend fun addToFavourite(id: String) {
        favouriteDao.insert(toEntity(id))
    }

    override suspend fun toggle(productId: String): Boolean {
        return if (favouriteDao.isFavourite(productId)) {
            favouriteDao.remove(productId)
            false
        } else {
            favouriteDao.insert(toEntity(productId))
            true
        }
    }

}