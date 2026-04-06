package com.example.myshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshop.data.cart.local.dao.CartDao
import com.example.myshop.data.cart.local.entity.CartItemEntity
import com.example.myshop.data.favourite.local.dao.FavouriteDao
import com.example.myshop.data.favourite.local.entity.FavouriteItemEntity

@Database(
    entities = [CartItemEntity::class, FavouriteItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun favouriteDao(): FavouriteDao

}