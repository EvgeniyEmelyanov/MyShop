package com.example.myshop.features.productdetail.presentation

import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.favourite.usecase.ObserveFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeFavouriteRepository
import com.example.myshop.testutil.FakeProductRepository
import com.example.myshop.testutil.MainDispatcherRule
import com.example.myshop.testutil.cartWith
import com.example.myshop.testutil.favouriteWith
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
class ProductDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `preview changes locally before product is added to cart`() =
        runTest(mainDispatcherRule.dispatcher) {
            val productRepository = FakeProductRepository(listOf(testProduct()))
            val cartRepository = FakeCartRepository()
            val favouriteRepository = FakeFavouriteRepository()
            val viewModel = createViewModel(
                productRepository,
                cartRepository,
                favouriteRepository
            )

            viewModel.setProductId("apple")
            advanceUntilIdle()
            assertEquals("1 pcs", viewModel.state.value.countText)
            assertEquals("1.00 $", viewModel.state.value.price)
            assertFalse(viewModel.state.value.isCart)

            viewModel.onPlus()
            advanceUntilIdle()

            assertEquals("2 pcs", viewModel.state.value.countText)
            assertEquals("2.00 $", viewModel.state.value.price)
            assertFalse(viewModel.state.value.isCart)
        }

    @Test
    fun `cart and favourite flows update product state`() =
        runTest(mainDispatcherRule.dispatcher) {
            val productRepository = FakeProductRepository(listOf(testProduct()))
            val cartRepository = FakeCartRepository()
            val favouriteRepository = FakeFavouriteRepository()
            val viewModel = createViewModel(
                productRepository,
                cartRepository,
                favouriteRepository
            )
            viewModel.setProductId("apple")
            advanceUntilIdle()

            cartRepository.emit(cartWith(amount = Amount.Piece(3)))
            favouriteRepository.emit(favouriteWith())
            advanceUntilIdle()

            val state = viewModel.state.value
            assertEquals(ContentState.CONTENT, state.contentState)
            assertTrue(state.isCart)
            assertTrue(state.isFavorite)
            assertFalse(state.isAddEnabled)
            assertEquals("Added", state.addButtonText)
            assertEquals("3 pcs", state.countText)
            assertEquals("3.00 $", state.price)
        }

    private fun createViewModel(
        productRepository: FakeProductRepository,
        cartRepository: FakeCartRepository,
        favouriteRepository: FakeFavouriteRepository
    ): ProductDetailViewModel {
        val linePriceCalculator = LinePriceCalculator()
        return ProductDetailViewModel(
            getProductByIdUseCase = GetProductByIdUseCase(productRepository),
            increaseAmountUseCase = IncreaseAmountUseCase(cartRepository),
            decreaseAmountUseCase = DecreaseAmountUseCase(cartRepository),
            calculateCartTotalsUseCase = CalculateCartTotalsUseCase(
                cartRepository,
                productRepository,
                linePriceCalculator
            ),
            quantityFormatter = QuantityFormatter(),
            moneyFormatter = MoneyFormatter(),
            linePriceCalculator = linePriceCalculator,
            imageKeyResolver = ImageKeyResolver(),
            toggleFavouriteUseCase = ToggleFavouriteUseCase(favouriteRepository),
            addProductToCartIfAbsentUseCase = AddProductToCartIfAbsentUseCase(
                GetCartUseCase(cartRepository),
                GetProductByIdUseCase(productRepository),
                AddProductToCartUseCase(cartRepository),
                DefaultCartAmountFactory()
            ),
            defaultCartAmountFactory = DefaultCartAmountFactory(),
            observeCartUseCase = ObserveCartUseCase(cartRepository),
            observeFavouriteUseCase = ObserveFavouriteUseCase(favouriteRepository)
        )
    }
}
