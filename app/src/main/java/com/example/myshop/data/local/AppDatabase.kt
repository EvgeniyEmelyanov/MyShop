package com.example.myshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshop.data.cart.local.dao.CartDao
import com.example.myshop.data.cart.local.entity.CartItemEntity
import com.example.myshop.data.favourite.local.dao.FavouriteDao
import com.example.myshop.data.favourite.local.entity.FavouriteItemEntity
import com.example.myshop.data.order.dao.OrderDao
import com.example.myshop.data.order.dao.OrderItemDao
import com.example.myshop.data.order.entity.OrderEntity
import com.example.myshop.data.order.entity.OrderItemEntity

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
