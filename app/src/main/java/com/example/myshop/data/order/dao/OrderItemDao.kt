package com.example.myshop.data.order.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myshop.data.order.entity.OrderItemEntity

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItem: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItemsByOrderId(orderId: String): List<OrderItemEntity>
}
