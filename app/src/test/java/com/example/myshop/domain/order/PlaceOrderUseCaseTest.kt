package com.example.myshop.domain.order

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.deliveryAddress.usecase.GetAddressByIdUseCase
import com.example.myshop.domain.order.model.FulfillmentSelection
import com.example.myshop.domain.order.model.OrderFulfillment
import com.example.myshop.domain.order.service.OrderIdGenerator
import com.example.myshop.domain.order.usecase.PlaceOrderUseCase
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeDeliveryRepository
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

        val result = placeOrderUseCase(FulfillmentSelection.Pickup)
        val order = requireNotNull(result)

        assertEquals(1, orderRepository.savedOrders.size)
        assertEquals(order, orderRepository.savedOrders.single())
        assertEquals("apple", order.items.single().productId)
        assertEquals(200L, order.total.cents)
        assertEquals(Currency.USD, order.total.currency)
        assertEquals(OrderFulfillment.Pickup, order.fulfillment)
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

        val createdOrder = placeOrderUseCase(FulfillmentSelection.Pickup)
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

        val result = placeOrderUseCase(FulfillmentSelection.Pickup)

        assertNull(result)
        assertTrue(orderRepository.savedOrders.isEmpty())
    }

    @Test
    fun `delivery stores address snapshot in order`() = runBlocking {
        val address = DeliveryAddress(
            id = 7, type = DeliveryAddressType.HOME, settlement = "Minsk",
            street = "Central", house = "2", building = "A", apartment = "4",
            isDefault = true
        )
        val deliveryRepository = FakeDeliveryRepository(listOf(address))
        val cartRepository = FakeCartRepository(cartWith())
        val orderRepository = FakeOrderRepository()
        val useCase = createPlaceOrderUseCase(
            cartRepository, FakeProductRepository(listOf(testProduct())),
            orderRepository, deliveryRepository
        )

        val order = requireNotNull(useCase(FulfillmentSelection.Delivery(address.id)))
        deliveryRepository.deleteAddress(address.id)

        assertEquals(
            OrderFulfillment.Delivery(
                address.type, address.settlement, address.street,
                address.house, address.building, address.apartment
            ),
            order.fulfillment
        )
        assertEquals(order, orderRepository.savedOrders.single())
    }

    @Test
    fun `missing delivery address does not save order or clear cart`() = runBlocking {
        val cartRepository = FakeCartRepository(cartWith())
        val orderRepository = FakeOrderRepository()
        val useCase = createPlaceOrderUseCase(
            cartRepository, FakeProductRepository(listOf(testProduct())), orderRepository
        )

        assertNull(useCase(FulfillmentSelection.Delivery(99)))
        assertTrue(orderRepository.savedOrders.isEmpty())
        assertEquals(1, cartRepository.getCart().items.size)
    }
}

private fun createPlaceOrderUseCase(
    cartRepository: FakeCartRepository,
    productRepository: FakeProductRepository,
    orderRepository: FakeOrderRepository,
    deliveryRepository: FakeDeliveryRepository = FakeDeliveryRepository()
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
        orderIdGenerator = OrderIdGenerator(),
        getAddressByIdUseCase = GetAddressByIdUseCase(deliveryRepository)
    )
}

