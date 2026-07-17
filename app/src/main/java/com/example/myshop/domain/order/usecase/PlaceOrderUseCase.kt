package com.example.myshop.domain.order.usecase


import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.model.OrderItem
import com.example.myshop.domain.order.model.OrderStatus
import com.example.myshop.domain.order.model.randomOrderStatus
import com.example.myshop.domain.order.repository.OrderRepository
import com.example.myshop.domain.order.service.OrderIdGenerator
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import javax.inject.Inject


class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val getCartUseCase: GetCartUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val clearProductsUseCase: ClearProductsUseCase,
    private val orderIdGenerator: OrderIdGenerator
) {

    suspend operator fun invoke(): Order? {
        val cart = getCartUseCase()

        if (cart.items.isEmpty()) return null

        val cartTotals = calculateCartTotalsUseCase.execute(cart)
        val orderItems = mutableListOf<OrderItem>()

        for (item in cart.items) {
            val product = getProductByIdUseCase(item.productId) ?: continue
            val lineTotal = cartTotals.lineTotals[item.productId] ?: continue

            orderItems.add(
                OrderItem(
                    productId = product.id,
                    title = product.title,
                    subtitle = product.subtitle,
                    imageKey = product.imageKey,
                    amount = item.amount,
                    lineTotal = lineTotal
                )
            )
        }

        if (orderItems.isEmpty()) return null

        val order = Order(
            id = orderIdGenerator(),
            createdAtMillis = System.currentTimeMillis(),
            status = randomOrderStatus(), // Fake local status until backend/admin panel exists.
            items = orderItems,
            total = cartTotals.total
        )

        orderRepository.saveOrder(order)
        clearProductsUseCase()

        return order
    }
}