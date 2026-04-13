package com.example.myshop.data.favourite.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_items")
data class FavouriteItemEntity(
    @PrimaryKey
    val productId: String
)