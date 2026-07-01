package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import com.example.myshop.domain.favourite.model.FavouriteItem
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AddAllFavouriteToCartUseCaseTest {

    @Test
    fun `addAll adds favourite products that are not in cart`() = runBlocking {
        val cartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = cartRepository,
            favouriteRepository = FakeFavouriteRepository(
                favourite = Favourite(
                    items = listOf(
                        FavouriteItem(productId = "apple"),
                        FavouriteItem(productId = "banana")
                    )
                )
            ),
            productRepository = FakeProductRepository(
                products = listOf(
                    product(
                        id = "apple",
                        amountType = AmountType.PIECE
                    ),
                    product(
                        id = "banana",
                        amountType = AmountType.WEIGHT
                    )
                )
            )
        )

        val result = useCase.addAll()

        assertEquals(2, result)
        assertEquals(
            listOf(
                AddedCartItem(productId = "apple", amount = Amount.Piece(1)),
                AddedCartItem(productId = "banana", amount = Amount.Grams(1000))
            ),
            cartRepository.addedItems
        )
    }

    @Test
    fun `addAll skips products that are already in cart`() = runBlocking {
        val cartRepository = FakeCartRepository(
            cart = Cart(
                items = listOf(
                    CartItem(productId = "apple", amount = Amount.Piece(1))
                )
            )
        )
        val useCase = useCase(
            cartRepository = cartRepository,
            favouriteRepository = FakeFavouriteRepository(
                favourite = Favourite(
                    items = listOf(
                        FavouriteItem(productId = "apple"),
                        FavouriteItem(productId = "banana")
                    )
                )
            ),
            productRepository = FakeProductRepository(
                products = listOf(
                    product(
                        id = "apple",
                        amountType = AmountType.PIECE
                    ),
                    product(
                        id = "banana",
                        amountType = AmountType.PIECE
                    )
                )
            )
        )

        val result = useCase.addAll()

        assertEquals(1, result)
        assertEquals(
            listOf(
                AddedCartItem(productId = "banana", amount = Amount.Piece(1))
            ),
            cartRepository.addedItems
        )
    }

    @Test
    fun `addAll skips favourite products that do not exist in catalog`() = runBlocking {
        val cartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = cartRepository,
            favouriteRepository = FakeFavouriteRepository(
                favourite = Favourite(
                    items = listOf(
                        FavouriteItem(productId = "missing_product")
                    )
                )
            ),
            productRepository = FakeProductRepository(products = emptyList())
        )

        val result = useCase.addAll()

        assertEquals(0, result)
        assertEquals(emptyList<AddedCartItem>(), cartRepository.addedItems)
    }

    @Test
    fun `addAll adds duplicate favourite product only once`() = runBlocking {
        val cartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = cartRepository,
            favouriteRepository = FakeFavouriteRepository(
                favourite = Favourite(
                    items = listOf(
                        FavouriteItem(productId = "apple"),
                        FavouriteItem(productId = "apple")
                    )
                )
            ),
            productRepository = FakeProductRepository(
                products = listOf(
                    product(
                        id = "apple",
                        amountType = AmountType.PIECE
                    )
                )
            )
        )

        val result = useCase.addAll()

        assertEquals(1, result)
        assertEquals(
            listOf(
                AddedCartItem(productId = "apple", amount = Amount.Piece(1))
            ),
            cartRepository.addedItems
        )
    }

    private fun useCase(
        cartRepository: FakeCartRepository,
        favouriteRepository: FakeFavouriteRepository,
        productRepository: FakeProductRepository
    ): AddAllFavouriteToCartUseCase {
        return AddAllFavouriteToCartUseCase(
            cartRepository = cartRepository,
            favouriteRepository = favouriteRepository,
            productRepository = productRepository,
            defaultCartAmountFactory = DefaultCartAmountFactory()
        )
    }

    private class FakeCartRepository(
        private val cart: Cart
    ) : CartRepository {

        val addedItems = mutableListOf<AddedCartItem>()

        override suspend fun getCart(): Cart = cart

        override fun observeCart(): Flow<Cart> {
            return flowOf(cart)
        }

        override suspend fun addToCart(productId: String, amount: Amount) {
            addedItems += AddedCartItem(productId, amount)
        }

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) = Unit
    }

    private class FakeFavouriteRepository(
        private val favourite: Favourite
    ) : FavouriteRepository {

        override suspend fun getFavourite(): Favourite = favourite

        override fun observeFavourite(): Flow<Favourite> {
            return flowOf(favourite)
        }

        override suspend fun addToFavourite(id: String) = Unit

        override suspend fun removeFavouriteItem(id: String) = Unit

        override suspend fun clearFavourite() = Unit

        override suspend fun isFavourite(id: String): Boolean {
            return favourite.items.any { it.productId == id }
        }

        override suspend fun toggle(productId: String): Boolean = false
    }

    private class FakeProductRepository(
        products: List<Product>
    ) : ProductRepository {

        private val productsById = products.associateBy { it.id }

        override suspend fun getAllProducts(): List<Product> = productsById.values.toList()

        override suspend fun getById(id: String): Product? = productsById[id]

        override suspend fun getProductsByCategory(category: Category): List<Product> {
            return productsById.values.filter { it.category == category }
        }
    }

    private data class AddedCartItem(
        val productId: String,
        val amount: Amount
    )

    private fun product(
        id: String,
        amountType: AmountType
    ): Product {
        return Product(
            id = id,
            title = id,
            subtitle = "",
            description = "",
            imageKey = "",
            price = Money(cents = 100, currency = Currency.USD),
            amountType = amountType,
            pricingUnit = when (amountType) {
                AmountType.PIECE -> PricingUnit.PER_ITEM
                AmountType.WEIGHT -> PricingUnit.PER_KG
            },
            tags = emptySet<ProductTag>(),
            category = Category.FRUITS_VEGETABLES,
            brand = Brand.FRESHFIELD
        )
    }
}
