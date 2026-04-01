package com.example.myshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshop.data.cart.local.CartDao
import com.example.myshop.data.cart.local.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}