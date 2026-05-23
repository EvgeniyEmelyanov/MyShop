package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class CalculateCartTotalsUseCaseTest {

    @Test
    fun `execute returns zero total for empty cart`() = runBlocking {
        val useCase = CalculateCartTotalsUseCase(
            cartRepository = FakeCartRepository(Cart()),
            productRepository = FakeProductRepository(emptyList()),
            linePriceCalculator = LinePriceCalculator()
        )

        val result = useCase.execute()

        assertEquals(0, result.total.cents)
        assertEquals(Currency.USD, result.total.currency)
        assertEquals(emptyMap<String, Money>(), result.lineTotals)
    }

    @Test
    fun `execute calculates total for multiple products`() = runBlocking {
        val apple = product(
            id = "apple",
            priceCents = 499,
            pricingUnit = PricingUnit.PER_ITEM
        )
        val banana = product(
            id = "banana",
            priceCents = 300,
            pricingUnit = PricingUnit.PER_KG
        )

        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(2)),
                CartItem(productId = "banana", amount = Amount.Grams(500))
            )
        )

        val useCase = CalculateCartTotalsUseCase(
            cartRepository = FakeCartRepository(cart),
            productRepository = FakeProductRepository(listOf(apple, banana)),
            linePriceCalculator = LinePriceCalculator()
        )

        val result = useCase.execute()

        assertEquals(998, result.lineTotals.getValue("apple").cents)
        assertEquals(150, result.lineTotals.getValue("banana").cents)
        assertEquals(1148, result.total.cents)
    }

    @Test
    fun `execute skips cart item when product does not exist`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "missing_product", amount = Amount.Piece(1))
            )
        )

        val useCase = CalculateCartTotalsUseCase(
            cartRepository = FakeCartRepository(cart),
            productRepository = FakeProductRepository(emptyList()),
            linePriceCalculator = LinePriceCalculator()
        )

        val result = useCase.execute()

        assertEquals(0, result.total.cents)
        assertFalse(result.lineTotals.containsKey("missing_product"))
    }

    private class FakeCartRepository(
        private val cart: Cart
    ) : CartRepository {

        override suspend fun getCart(): Cart = cart

        override suspend fun addToCart(productId: String, amount: Amount) = Unit

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) = Unit
    }

    private class FakeProductRepository(
        products: List<Product>
    ) : ProductRepository {

        private val productsById = products.associateBy { it.id }

        override fun getAllProducts(): List<Product> = productsById.values.toList()

        override fun getById(id: String): Product? = productsById[id]

        override fun getProductsByCategory(category: Category): List<Product> {
            return productsById.values.filter { it.category == category }
        }
    }

    private fun product(
        id: String,
        priceCents: Long,
        pricingUnit: PricingUnit
    ): Product {
        return Product(
            id = id,
            title = id,
            subtitle = "",
            description = "",
            imageKey = "",
            price = Money(priceCents, Currency.USD),
            amountType = when (pricingUnit) {
                PricingUnit.PER_ITEM -> AmountType.PIECE
                PricingUnit.PER_KG -> AmountType.WEIGHT
            },
            pricingUnit = pricingUnit,
            tags = emptySet<ProductTag>(),
            category = Category.FRUITS_VEGETABLES
        )
    }
}
