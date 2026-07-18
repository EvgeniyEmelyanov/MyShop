package com.example.myshop.domain.order

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.order.service.OrderIdGenerator
import com.example.myshop.domain.order.usecase.PlaceOrderUseCase
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeOrderRepository
import com.example.myshop.testutil.FakeProductRepository
import com.example.myshop.testutil.cartWith
import com.example.myshop.testutil.testProduct
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PlaceOrderUseCaseTest {

    @Test
    fun `invoke creates order saves it and clears cart`() = runBlocking {
        val productRepository = FakeProductRepository(listOf(testProduct()))
        val cartRepository = FakeCartRepository(cartWith(amount = Amount.Piece(2)))
        val orderRepository = FakeOrderRepository()
        val placeOrderUseCase = createPlaceOrderUseCase(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            productRepository = productRepository
        )

        val result = placeOrderUseCase()
        val order = requireNotNull(result)

        assertEquals(1, orderRepository.savedOrders.size)
        assertEquals(order, orderRepository.savedOrders.single())
        assertEquals("apple", order.items.single().productId)
        assertEquals(200L, order.total.cents)
        assertEquals(Currency.USD, order.total.currency)
        assertTrue(cartRepository.getCart().items.isEmpty())

    }

    @Test
    fun `invoke saves order that can be observed from repository flow`() = runBlocking {
        val productRepository = FakeProductRepository(listOf(testProduct()))
        val cartRepository = FakeCartRepository(cartWith(amount = Amount.Piece(2)))
        val orderRepository = FakeOrderRepository()
        val placeOrderUseCase = createPlaceOrderUseCase(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            productRepository = productRepository
        )

        val createdOrder = placeOrderUseCase()
        val ordersFromFlow = orderRepository.observeOrders().first()

        assertEquals(listOf(createdOrder), ordersFromFlow)

    }

    @Test
    fun `invoke returns null and does not save order when cart is empty`() = runBlocking {
        val productRepository = FakeProductRepository(listOf(testProduct()))
        val cartRepository = FakeCartRepository(Cart())
        val orderRepository = FakeOrderRepository()
        val placeOrderUseCase = createPlaceOrderUseCase(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            productRepository = productRepository
        )

        val result = placeOrderUseCase()

        assertNull(result)
        assertTrue(orderRepository.savedOrders.isEmpty())
    }
}

private fun createPlaceOrderUseCase(
    cartRepository: FakeCartRepository,
    productRepository: FakeProductRepository,
    orderRepository: FakeOrderRepository
): PlaceOrderUseCase {
    return PlaceOrderUseCase(
        orderRepository = orderRepository,
        getCartUseCase = GetCartUseCase(cartRepository),
        calculateCartTotalsUseCase = CalculateCartTotalsUseCase(
            cartRepository = cartRepository,
            productRepository = productRepository,
            linePriceCalculator = LinePriceCalculator()
        ),
        getProductByIdUseCase = GetProductByIdUseCase(productRepository),
        clearProductsUseCase = ClearProductsUseCase(cartRepository),
        orderIdGenerator = OrderIdGenerator()
    )
}

