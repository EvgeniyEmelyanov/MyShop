package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.repository.ProductRepository
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AddProductToCartIfAbsentUseCaseTest {

    @Test
    fun `invoke returns ProductNotFound when product does not exist`() = runBlocking {
        val fakeCartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = fakeCartRepository,
            productRepository = FakeProductRepository(products = emptyList())
        )

        val result = useCase("missing_product")

        assertEquals(AddToCartResult.ProductNotFound, result)
        assertNull(fakeCartRepository.addedProductId)
        assertNull(fakeCartRepository.addedAmount)
    }

    @Test
    fun `invoke returns AlreadyInCart when product is already in cart`() = runBlocking {
        val product = product(
            id = "apple",
            title = "Apple",
            amountType = AmountType.PIECE
        )
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(1))
            )
        )
        val fakeCartRepository = FakeCartRepository(cart = cart)
        val useCase = useCase(
            cartRepository = fakeCartRepository,
            productRepository = FakeProductRepository(products = listOf(product))
        )

        val result = useCase("apple")

        assertEquals(AddToCartResult.AlreadyInCart(productTitle = "Apple"), result)
        assertNull(fakeCartRepository.addedProductId)
        assertNull(fakeCartRepository.addedAmount)
    }

    @Test
    fun `invoke adds piece product when it is not in cart`() = runBlocking {
        val product = product(
            id = "apple",
            title = "Apple",
            amountType = AmountType.PIECE
        )
        val fakeCartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = fakeCartRepository,
            productRepository = FakeProductRepository(products = listOf(product))
        )

        val result = useCase("apple")

        assertEquals(AddToCartResult.Added(productTitle = "Apple"), result)
        assertEquals("apple", fakeCartRepository.addedProductId)
        assertEquals(Amount.Piece(1), fakeCartRepository.addedAmount)
    }

    @Test
    fun `invoke adds weight product when it is not in cart`() = runBlocking {
        val product = product(
            id = "banana",
            title = "Banana",
            amountType = AmountType.WEIGHT
        )
        val fakeCartRepository = FakeCartRepository(cart = Cart())
        val useCase = useCase(
            cartRepository = fakeCartRepository,
            productRepository = FakeProductRepository(products = listOf(product))
        )

        val result = useCase("banana")

        assertEquals(AddToCartResult.Added(productTitle = "Banana"), result)
        assertEquals("banana", fakeCartRepository.addedProductId)
        assertEquals(Amount.Grams(1000), fakeCartRepository.addedAmount)
    }

    private fun useCase(
        cartRepository: FakeCartRepository,
        productRepository: FakeProductRepository
    ): AddProductToCartIfAbsentUseCase {
        return AddProductToCartIfAbsentUseCase(
            getCartUseCase = GetCartUseCase(cartRepository),
            getProductByIdUseCase = GetProductByIdUseCase(productRepository),
            addProductToCartUseCase = AddProductToCartUseCase(cartRepository),
            defaultCartAmountFactory = DefaultCartAmountFactory()
        )
    }

    private class FakeCartRepository(
        private val cart: Cart
    ) : CartRepository {

        var addedProductId: String? = null
        var addedAmount: Amount? = null

        override suspend fun getCart(): Cart = cart

        override suspend fun addToCart(productId: String, amount: Amount) {
            addedProductId = productId
            addedAmount = amount
        }

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) = Unit
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

    private fun product(
        id: String,
        title: String,
        amountType: AmountType
    ): Product {
        return Product(
            id = id,
            title = title,
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
            category = Category.FRUITS_VEGETABLES
        )
    }
}
