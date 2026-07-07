package com.example.myshop.features.shop.presentation

import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeProductRepository
import com.example.myshop.testutil.MainDispatcherRule
import com.example.myshop.testutil.cartWith
import com.example.myshop.testutil.testProduct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShopViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `cart updates product card without reloading catalog`() =
        runTest(mainDispatcherRule.dispatcher) {
            val productRepository = FakeProductRepository(
                listOf(testProduct(tags = setOf(ProductTag.EXCLUSIVE_OFFER)))
            )
            val cartRepository = FakeCartRepository()
            val viewModel = createViewModel(productRepository, cartRepository)

            viewModel.load()
            advanceUntilIdle()
            assertFalse(viewModel.state.value.exclusiveOffers.single().inCart)

            cartRepository.emit(cartWith())
            advanceUntilIdle()

            assertTrue(viewModel.state.value.exclusiveOffers.single().inCart)
            assertEquals(ContentState.CONTENT, viewModel.state.value.contentState)
        }

    @Test
    fun `load exposes error when catalog fails`() = runTest(mainDispatcherRule.dispatcher) {
        val productRepository = FakeProductRepository(
            products = emptyList(),
            loadError = IllegalStateException("Catalog unavailable")
        )
        val viewModel = createViewModel(productRepository, FakeCartRepository())

        viewModel.load()
        advanceUntilIdle()

        assertEquals(ContentState.ERROR, viewModel.state.value.contentState)
    }

    private fun createViewModel(
        productRepository: FakeProductRepository,
        cartRepository: FakeCartRepository
    ): ShopViewModel {
        return ShopViewModel(
            getAllProductsUseCase = GetAllProductsUseCase(productRepository),
            moneyFormatter = MoneyFormatter(),
            imageKeyResolver = ImageKeyResolver(),
            addProductToCartIfAbsentUseCase = AddProductToCartIfAbsentUseCase(
                GetCartUseCase(cartRepository),
                GetProductByIdUseCase(productRepository),
                AddProductToCartUseCase(cartRepository),
                DefaultCartAmountFactory()
            ),
            observeCartUseCase = ObserveCartUseCase(cartRepository)
        )
    }
}
