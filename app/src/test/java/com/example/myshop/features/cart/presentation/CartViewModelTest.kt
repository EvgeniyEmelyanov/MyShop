package com.example.myshop.features.cart.presentation

import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.cart.usecase.RemoveProductUseCase
import com.example.myshop.domain.cart.usecase.SetAmountUseCase
import com.example.myshop.domain.order.usecase.PlaceOrderUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeProductRepository
import com.example.myshop.testutil.MainDispatcherRule
import com.example.myshop.testutil.cartWith
import com.example.myshop.testutil.testProduct
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `cart state reacts to amount changes`() = runTest(mainDispatcherRule.dispatcher) {
        val productRepository = FakeProductRepository(listOf(testProduct()))
        val cartRepository = FakeCartRepository(cartWith())
        val viewModel = createViewModel(productRepository, cartRepository)
        advanceUntilIdle()

        assertEquals(ContentState.CONTENT, viewModel.state.value.contentState)
        assertEquals("1 pcs", viewModel.state.value.items.single().quantityText)
        assertEquals("1.00 $", viewModel.state.value.totalString)

        viewModel.increaseAmount("apple")
        advanceUntilIdle()

        assertEquals("2 pcs", viewModel.state.value.items.single().quantityText)
        assertEquals("2.00 $", viewModel.state.value.totalString)
    }

    @Test
    fun `place order clears cart and emits event`() = runTest(mainDispatcherRule.dispatcher) {
        val productRepository = FakeProductRepository(listOf(testProduct()))
        val cartRepository = FakeCartRepository(cartWith())
        val viewModel = createViewModel(productRepository, cartRepository)
        advanceUntilIdle()
        val event = async(start = CoroutineStart.UNDISPATCHED) {
            viewModel.orderPlacedEvent.first()
        }

        viewModel.placeOrder()
        advanceUntilIdle()

        event.await()
        assertTrue(viewModel.state.value.items.isEmpty())
        assertEquals(ContentState.EMPTY, viewModel.state.value.contentState)
    }

    private fun createViewModel(
        productRepository: FakeProductRepository,
        cartRepository: FakeCartRepository
    ): CartViewModel {
        val linePriceCalculator = LinePriceCalculator()
        return CartViewModel(
            getProductByIdUseCase = GetProductByIdUseCase(productRepository),
            observeCartUseCase = ObserveCartUseCase(cartRepository),
            removeProductUseCase = RemoveProductUseCase(cartRepository),
            increaseAmountUseCase = IncreaseAmountUseCase(cartRepository),
            decreaseAmountUseCase = DecreaseAmountUseCase(cartRepository),
            imageKeyResolver = ImageKeyResolver(),
            quantityFormatter = QuantityFormatter(),
            calculateCartTotalsUseCase = CalculateCartTotalsUseCase(
                cartRepository,
                productRepository,
                linePriceCalculator
            ),
            moneyFormatter = MoneyFormatter()
        )

    }
}
