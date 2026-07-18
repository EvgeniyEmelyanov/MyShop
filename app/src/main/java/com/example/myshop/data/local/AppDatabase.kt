package com.example.myshop.data.local

import androidx.room.*
import com.example.myshop.data.cart.local.dao.*
import com.example.myshop.data.cart.local.entity.*
import com.example.myshop.data.favourite.local.dao.*
import com.example.myshop.data.favourite.local.entity.*
import com.example.myshop.data.order.dao.*
import com.example.myshop.data.order.entity.*

@Database(
    entities = [CartItemEntity::class, FavouriteItemEntity::class, OrderEntity::class, OrderItemEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun favouriteDao(): FavouriteDao

    abstract fun orderDao(): OrderDao

    abstract fun orderItemDao(): OrderItemDao

}
