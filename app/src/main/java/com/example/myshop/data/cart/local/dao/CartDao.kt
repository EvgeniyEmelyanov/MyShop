package com.example.myshop.data.cart.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myshop.data.cart.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY sortOrder ASC")
    suspend fun getAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items ORDER BY sortOrder ASC")
    fun observeAll(): Flow<List<CartItemEntity>>


    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getByProductId(productId: String): CartItemEntity?

    @Query("SELECT MAX(sortOrder) FROM cart_items")
    suspend fun getMaxSortOrder(): Long?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: CartItemEntity)

    @Query(
        """
        UPDATE cart_items
        SET amountType = :amountType, amountValue = :amountValue
        WHERE productId = :productId
        """
    )
    suspend fun updateAmount(
        productId: String,
        amountType: String,
        amountValue: Long
    )

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun remove(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}
