package com.example.myshop.features.explore.presentation

import com.example.myshop.core.filter.ProductFilter
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModelTest {

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `product inCart updates without resetting search`() = runTest(testDispatcher) {
        val productRepository = FakeProductRepository(listOf(product("apple", "Apple")))
        val cartRepository = FakeCartRepository(Cart())
        val viewModel = createViewModel(productRepository, cartRepository)

        viewModel.load()
        advanceUntilIdle()
        viewModel.onSearchQueryChanged("Apple")
        advanceUntilIdle()

        val stateBeforeCartUpdate = viewModel.state.value
        assertEquals("Apple", stateBeforeCartUpdate.searchQuery)
        assertEquals(1, stateBeforeCartUpdate.products.size)
        assertFalse(stateBeforeCartUpdate.products.single().inCart)

        cartRepository.emit(cartWith())
        advanceUntilIdle()

        val stateAfterCartUpdate = viewModel.state.value
        assertEquals("Apple", stateAfterCartUpdate.searchQuery)
        assertEquals(1, stateAfterCartUpdate.products.size)
        assertTrue(stateAfterCartUpdate.products.single().inCart)

        cartRepository.emit(Cart())
        advanceUntilIdle()

        val stateAfterCartCleared = viewModel.state.value
        assertEquals("Apple", stateAfterCartCleared.searchQuery)
        assertEquals(1, stateAfterCartCleared.products.size)
        assertFalse(stateAfterCartCleared.products.single().inCart)
    }

    @Test
    fun `load exposes error state when products cannot be loaded`() = runTest(testDispatcher) {
        val productRepository = FakeProductRepository(
            products = emptyList(),
            loadError = IllegalStateException("Catalog unavailable")
        )
        val viewModel = createViewModel(productRepository, FakeCartRepository(Cart()))

        viewModel.load()
        advanceUntilIdle()

        assertEquals(ContentState.ERROR, viewModel.state.value.contentState)
    }

    @Test
    fun `search applies only the latest query`() = runTest(testDispatcher) {
        val productRepository = FakeProductRepository(
            listOf(
                product("apple", "Apple"),
                product("banana", "Banana")
            )
        )
        val viewModel = createViewModel(productRepository, FakeCartRepository(Cart()))

        viewModel.load()
        advanceUntilIdle()
        viewModel.onSearchQueryChanged("Apple")
        viewModel.onSearchQueryChanged("Banana")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Banana", state.searchQuery)
        assertEquals("banana", state.products.single().id)
    }

    @Test
    fun `blank search shows categories and no products`() = runTest(testDispatcher) {
        val productRepository = FakeProductRepository(listOf(product("apple", "Apple")))
        val viewModel = createViewModel(productRepository, FakeCartRepository(Cart()))

        viewModel.load()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isSearchMode)
        assertTrue(state.categories.isNotEmpty())
        assertTrue(state.products.isEmpty())
        assertEquals(ContentState.CONTENT, state.contentState)
    }

    @Test
    fun `already in cart emits toast message`() = runTest(testDispatcher) {
        val productRepository = FakeProductRepository(listOf(product("apple", "Apple")))
        val cartRepository = FakeCartRepository(cartWith())
        val viewModel = createViewModel(productRepository, cartRepository)
        advanceUntilIdle()
        val message = async(start = CoroutineStart.UNDISPATCHED) {
            viewModel.toastMessage.first()
        }

        viewModel.onAddToCart("apple")
        advanceUntilIdle()

        assertEquals("Apple is already in cart", message.await())
    }

    private fun createViewModel(
        productRepository: ProductRepository,
        cartRepository: CartRepository
    ): ExploreViewModel {
        return ExploreViewModel(
            getAllProductsUseCase = GetAllProductsUseCase(productRepository),
            imageKeyResolver = ImageKeyResolver(),
            moneyFormatter = MoneyFormatter(),
            addProductToCartIfAbsentUseCase = AddProductToCartIfAbsentUseCase(
                getCartUseCase = GetCartUseCase(cartRepository),
                getProductByIdUseCase = GetProductByIdUseCase(productRepository),
                addProductToCartUseCase = AddProductToCartUseCase(cartRepository),
                defaultCartAmountFactory = DefaultCartAmountFactory()
            ),
            productFilter = ProductFilter(),
            observeCartUseCase = ObserveCartUseCase(cartRepository)
        )
    }

    private class FakeCartRepository(initialCart: Cart) : CartRepository {
        private val cartFlow = MutableStateFlow(initialCart)

        fun emit(cart: Cart) {
            cartFlow.value = cart
        }

        override suspend fun getCart(): Cart = cartFlow.value

        override fun observeCart(): Flow<Cart> = cartFlow

        override suspend fun addToCart(productId: String, amount: Amount) = Unit

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) = Unit
    }

    private class FakeProductRepository(
        products: List<Product>,
        private val loadError: Exception? = null
    ) : ProductRepository {
        private val productsById = products.associateBy { it.id }

        override suspend fun getAllProducts(): List<Product> {
            loadError?.let { throw it }
            return productsById.values.toList()
        }

        override suspend fun getById(id: String): Product? = productsById[id]

        override suspend fun getProductsByCategory(category: Category): List<Product> {
            return productsById.values.filter { it.category == category }
        }
    }

    private fun cartWith(productId: String = "apple"): Cart {
        return Cart(
            items = listOf(
                CartItem(productId = productId, amount = Amount.Piece(1))
            )
        )
    }

    private fun product(id: String, title: String): Product {
        return Product(
            id = id,
            title = title,
            subtitle = "",
            description = "",
            imageKey = "",
            price = Money(100, Currency.USD),
            amountType = AmountType.PIECE,
            pricingUnit = PricingUnit.PER_ITEM,
            tags = emptySet(),
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        )
    }
}
