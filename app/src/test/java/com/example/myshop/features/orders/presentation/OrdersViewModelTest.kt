package com.example.myshop.features.orders.presentation

import com.example.myshop.core.formatter.DateFormatter
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.repository.OrderRepository
import com.example.myshop.domain.order.usecase.ObserveOrdersUseCase
import com.example.myshop.testutil.FakeOrderRepository
import com.example.myshop.testutil.MainDispatcherRule
import com.example.myshop.testutil.testOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `orders state reacts to order changes`() = runTest(mainDispatcherRule.dispatcher) {
        val fakeOrderRepository = FakeOrderRepository(listOf(testOrder()))
        val viewModel = createViewModel(fakeOrderRepository)
        advanceUntilIdle()

        val order = viewModel.state.value.orders.first()

        assertEquals(ContentState.CONTENT, viewModel.state.value.contentState)
        assertEquals(1, viewModel.state.value.orders.size)
        assertEquals("123", order.id)
        assertEquals("Processing", order.status)
        assertEquals(OrdersFilter.PROCESSING, order.statusFilter)
        assertEquals("1 item", order.itemsCount)
        assertEquals("1.00 $", order.total)
        assertEquals(1, order.productImages.size)
    }

    @Test
    fun `orders state is empty when repository has no orders`() =
        runTest(mainDispatcherRule.dispatcher) {
            val fakeOrderRepository = FakeOrderRepository()
            val viewModel = createViewModel(fakeOrderRepository)
            advanceUntilIdle()

            assertEquals(ContentState.EMPTY, viewModel.state.value.contentState)
            assertTrue(viewModel.state.value.orders.isEmpty())
        }

    @Test
    fun `orders state is error when repository throws exception`() =
        runTest(mainDispatcherRule.dispatcher) {
            val errorOrderRepository = ErrorOrderRepository()
            val viewModel = createViewModel(errorOrderRepository)
            advanceUntilIdle()

            assertEquals(ContentState.ERROR, viewModel.state.value.contentState)
            assertTrue(viewModel.state.value.orders.isEmpty())
        }
}

private fun createViewModel(
    orderRepository: OrderRepository
): OrdersViewModel {
    return OrdersViewModel(
        observeOrdersUseCase = ObserveOrdersUseCase(orderRepository),
        dateFormatter = DateFormatter(),
        moneyFormatter = MoneyFormatter(),
        imageKeyResolver = ImageKeyResolver()
    )
}

private class ErrorOrderRepository : OrderRepository {

    override suspend fun saveOrder(order: Order) = Unit

    override fun observeOrders(): Flow<List<Order>> {
        return flow {
            throw RuntimeException("Test exception")
        }
    }

    override suspend fun getOrderById(orderId: String): Order? = null
}
