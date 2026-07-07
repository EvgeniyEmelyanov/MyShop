package com.example.myshop.features.productsByCategory.presentation

import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.ProductFilter
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.domain.product.usecase.GetProductsByCategoryUseCase
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
class ProductsByCategoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `cart update preserves category filter and visible products`() =
        runTest(mainDispatcherRule.dispatcher) {
            val productRepository = FakeProductRepository(listOf(testProduct()))
            val cartRepository = FakeCartRepository()
            val viewModel = ProductsByCategoryViewModel(
                getCartUseCase = GetCartUseCase(cartRepository),
                addProductToCartUseCase = AddProductToCartUseCase(cartRepository),
                getProductByIdUseCase = GetProductByIdUseCase(productRepository),
                getProductsByCategoryUseCase = GetProductsByCategoryUseCase(productRepository),
                moneyFormatter = MoneyFormatter(),
                imageKeyResolver = ImageKeyResolver(),
                productFilter = ProductFilter(),
                observeCartUseCase = ObserveCartUseCase(cartRepository)
            )
            val filter = FilterParams(brands = setOf(Brand.FRESHFIELD))

            viewModel.setCategory(Category.FRUITS_VEGETABLES)
            advanceUntilIdle()
            viewModel.onFilterChanged(filter)
            advanceUntilIdle()
            assertFalse(viewModel.state.value.products.single().inCart)

            cartRepository.emit(cartWith())
            advanceUntilIdle()

            val state = viewModel.state.value
            assertEquals(filter, state.filterParams)
            assertEquals(1, state.products.size)
            assertTrue(state.products.single().inCart)
        }
}
