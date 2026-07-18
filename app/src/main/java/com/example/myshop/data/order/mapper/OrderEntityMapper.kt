package com.example.myshop.data.order.mapper

import com.example.myshop.data.order.entity.OrderEntity
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.model.OrderItem
import com.example.myshop.domain.order.model.OrderStatus
import com.example.myshop.domain.product.model.Currency

fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        id = this.id,
        createdAtMillis = this.createdAtMillis,
        status = this.status.name,
        totalCents = this.total.cents,
        currency = this.total.currency.name
    )
}

fun OrderEntity.toDomain(
    items: List<OrderItem>
): Order {
    return Order(
        id = id,
        createdAtMillis = createdAtMillis,
        status = OrderStatus.valueOf(status),
        items = items,
        total = Money(totalCents, Currency.valueOf(currency))
    )
}