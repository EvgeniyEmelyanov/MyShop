package com.example.myshop.data.order.repository

import com.example.myshop.data.order.dao.OrderDao
import com.example.myshop.data.order.dao.OrderItemDao
import com.example.myshop.data.order.mapper.toDomain
import com.example.myshop.data.order.mapper.toEntity
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) : OrderRepository {

    override suspend fun saveOrder(order: Order) {
        orderDao.insertOrder(order.toEntity())

        val orderItems = order.items.map { it.toEntity(order.id) }

        orderItemDao.insertOrderItems(orderItems)
    }

    override fun observeOrders(): Flow<List<Order>> {
        return orderDao.observeOrders().map { orders ->
            orders.map { order ->
                val orderItems = orderItemDao.getOrderItemsByOrderId(order.id).map { it.toDomain() }
                order.toDomain(orderItems)
            }
        }
    }

    override suspend fun getOrderById(orderId: String): Order? {
        orderDao.getOrderById(orderId)?.let { order ->
            val orderItems = orderItemDao.getOrderItemsByOrderId(orderId).map { it.toDomain() }
            return order.toDomain(orderItems)
        }
        return null
    }

}