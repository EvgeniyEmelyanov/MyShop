package com.example.myshop.data.favourite.local.mapper

import com.example.myshop.data.favourite.local.entity.FavouriteItemEntity
import com.example.myshop.domain.favourite.model.FavouriteItem

fun FavouriteItemEntity.toDomain(): FavouriteItem {
    return FavouriteItem(
        productId = productId
    )
}

fun toEntity(productId: String): FavouriteItemEntity {
    return FavouriteItemEntity(
        productId = productId
    )
}