package com.example.myshop.domain.order.usecase

import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(): Flow<List<Order>> {
        return orderRepository.observeOrders()
    }
}
