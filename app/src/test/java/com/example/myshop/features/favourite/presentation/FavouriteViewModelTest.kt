package com.example.myshop.features.favourite.presentation

import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.usecase.AddAllFavouriteToCartUseCase
import com.example.myshop.domain.favourite.usecase.ObserveFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.testutil.FakeCartRepository
import com.example.myshop.testutil.FakeFavouriteRepository
import com.example.myshop.testutil.FakeProductRepository
import com.example.myshop.testutil.MainDispatcherRule
import com.example.myshop.testutil.favouriteWith
import com.example.myshop.testutil.testProduct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `favourite flow updates content and empty states`() =
        runTest(mainDispatcherRule.dispatcher) {
            val productRepository = FakeProductRepository(listOf(testProduct()))
            val favouriteRepository = FakeFavouriteRepository()
            val viewModel = FavouriteViewModel(
                getProductByIdUseCase = GetProductByIdUseCase(productRepository),
                observeFavouriteUseCase = ObserveFavouriteUseCase(favouriteRepository),
                addAllFavouriteToCartUseCase = AddAllFavouriteToCartUseCase(
                    FakeCartRepository(),
                    favouriteRepository,
                    productRepository,
                    DefaultCartAmountFactory()
                ),
                imageKeyResolver = ImageKeyResolver(),
                moneyFormatter = MoneyFormatter()
            )
            advanceUntilIdle()
            assertEquals(ContentState.EMPTY, viewModel.state.value.contentState)

            favouriteRepository.emit(favouriteWith())
            advanceUntilIdle()

            assertEquals(ContentState.CONTENT, viewModel.state.value.contentState)
            assertEquals("apple", viewModel.state.value.items.single().productId)

            favouriteRepository.emit(com.example.myshop.domain.favourite.model.Favourite())
            advanceUntilIdle()

            assertEquals(ContentState.EMPTY, viewModel.state.value.contentState)
            assertTrue(viewModel.state.value.items.isEmpty())
        }
}
