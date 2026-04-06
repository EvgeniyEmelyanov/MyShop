package com.example.myshop.data.favourite.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myshop.data.favourite.local.entity.FavouriteItemEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite_items")
    suspend fun getAll(): List<FavouriteItemEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: FavouriteItemEntity)

    @Query("DELETE FROM favourite_items WHERE productId = :productId")
    suspend fun remove(productId: String)

    @Query("DELETE FROM favourite_items")
    suspend fun clear()

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_items WHERE productId = :productId)")
    suspend fun isFavourite(productId: String): Boolean

}