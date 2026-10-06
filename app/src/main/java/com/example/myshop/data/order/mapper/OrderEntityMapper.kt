package com.example.myshop.data.order.mapper

import com.example.myshop.data.order.entity.OrderEntity
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.model.OrderFulfillment
import com.example.myshop.domain.order.model.OrderItem
import com.example.myshop.domain.order.model.OrderStatus
import com.example.myshop.domain.product.model.Currency

fun Order.toEntity(): OrderEntity {
    val delivery = fulfillment as? OrderFulfillment.Delivery


    return OrderEntity(
        id = this.id,
        createdAtMillis = this.createdAtMillis,
        status = this.status.name,
        totalCents = this.total.cents,
        currency = this.total.currency.name,
        fulfillmentType = if (delivery == null) "PICKUP" else "DELIVERY",
        deliveryAddressType = delivery?.type?.name,
        deliverySettlement = delivery?.settlement,
        deliveryStreet = delivery?.street,
        deliveryHouse = delivery?.house,
        deliveryBuilding = delivery?.building,
        deliveryApartment = delivery?.apartment
    )
}

fun OrderEntity.toDomain(
    items: List<OrderItem>
): Order {
    val fulfillment = when (fulfillmentType) {
        "PICKUP" -> OrderFulfillment.Pickup
        "DELIVERY" -> OrderFulfillment.Delivery(
            type = DeliveryAddressType.valueOf(
                requireNotNull(deliveryAddressType)
            ),
            settlement = requireNotNull(deliverySettlement),
            street = requireNotNull(deliveryStreet),
            house = requireNotNull(deliveryHouse),
            building = deliveryBuilding,
            apartment = deliveryApartment
        )
        else -> error("Unknown order fulfillment type: $fulfillmentType")
    }

    return Order(
        id = id,
        createdAtMillis = createdAtMillis,
        status = OrderStatus.valueOf(status),
        items = items,
        total = Money(totalCents, Currency.valueOf(currency)),
        fulfillment = fulfillment,
    )
}