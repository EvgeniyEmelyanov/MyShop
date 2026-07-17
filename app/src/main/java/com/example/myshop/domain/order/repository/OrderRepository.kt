package com.example.myshop.domain.order.repository

import com.example.myshop.domain.order.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun saveOrder(order: Order)

    fun observeOrders(): Flow<List<Order>>

    suspend fun getOrderById(orderId: String): Order?
}