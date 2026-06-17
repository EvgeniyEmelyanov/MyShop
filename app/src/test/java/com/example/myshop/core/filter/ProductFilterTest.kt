package com.example.myshop.core.filter

import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductFilterTest {

    private val productFilter = ProductFilter()

    @Test
    fun `apply returns only products of selected brand`() {
        val products = listOf(
            product(id = "apple", brand = Brand.FRESHFIELD),
            product(id = "banana", brand = Brand.ORCHARD_LANE),
            product(id = "carrot", brand = Brand.FRESHFIELD)
        )
        val filterParams = FilterParams(
            brands = setOf(Brand.FRESHFIELD)
        )

        val result = productFilter.apply(products, filterParams)

        assertEquals(listOf("apple", "carrot"), result.map { product -> product.id })
    }

    @Test
    fun `apply returns only products of selected category`() {
        val products = listOf(
            product(id = "apple", category = Category.FRUITS_VEGETABLES),
            product(id = "milk", category = Category.BEVERAGES),
            product(id = "carrot", category = Category.FRUITS_VEGETABLES)
        )

        val filterParams = FilterParams(
            categories = setOf(Category.FRUITS_VEGETABLES)
        )

        val result = productFilter.apply(products, filterParams)

        assertEquals(listOf("apple", "carrot"), result.map { product -> product.id })
    }

    @Test
    fun `apply returns sorted products by high to low price`() {
        val products = listOf(
            product(id = "apple", price = Money(150, Currency.USD)),
            product(id = "milk", price = Money(200, Currency.USD)),
            product(id = "carrot", price = Money(250, Currency.USD))
        )

        val filterParams = FilterParams(
            priceSort = PriceSort.HIGH_TO_LOW
        )

        val result = productFilter.apply(products, filterParams)

        assertEquals(
            listOf(250L, 200L, 150L),
            result.map { product -> product.price.cents }
        )
    }

    @Test
    fun `apply returns sorted products by low to high price`() {
        val products = listOf(
            product(id = "apple", price = Money(250, Currency.USD)),
            product(id = "milk", price = Money(150, Currency.USD)),
            product(id = "carrot", price = Money(200, Currency.USD))
        )

        val result = productFilter.apply(
            products = products,
            filterParams = FilterParams(priceSort = PriceSort.LOW_TO_HIGH)
        )

        assertEquals(
            listOf(150L, 200L, 250L),
            result.map { product -> product.price.cents }
        )
    }

    @Test
    fun `apply returns products matching selected brand and category`() {
        val products = listOf(
            product(
                id = "apple",
                brand = Brand.FRESHFIELD,
                category = Category.FRUITS_VEGETABLES
            ),
            product(
                id = "juice",
                brand = Brand.FRESHFIELD,
                category = Category.BEVERAGES
            ),
            product(
                id = "banana",
                brand = Brand.ORCHARD_LANE,
                category = Category.FRUITS_VEGETABLES
            )
        )
        val filterParams = FilterParams(
            categories = setOf(Category.FRUITS_VEGETABLES),
            brands = setOf(Brand.FRESHFIELD)
        )

        val result = productFilter.apply(products, filterParams)

        assertEquals(listOf("apple"), result.map { product -> product.id })
    }

    @Test
    fun `apply returns empty list when no products match filters`() {
        val products = listOf(
            product(id = "apple", brand = Brand.FRESHFIELD),
            product(id = "banana", brand = Brand.ORCHARD_LANE)
        )

        val result = productFilter.apply(
            products = products,
            filterParams = FilterParams(brands = setOf(Brand.MEADOW_DAIRY))
        )

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun `apply returns all products in original order when filters are empty`() {
        val products = listOf(
            product(id = "apple"),
            product(id = "milk"),
            product(id = "carrot")
        )

        val result = productFilter.apply(products, FilterParams())

        assertEquals(products, result)
    }

    private fun product(
        id: String,
        brand: Brand = Brand.FRESHFIELD,
        category: Category = Category.FRUITS_VEGETABLES,
        price: Money = Money(100, currency = Currency.USD)
    ): Product {
        return Product(
            id = id,
            title = id,
            subtitle = "",
            description = "",
            imageKey = "",
            price = price,
            amountType = AmountType.PIECE,
            pricingUnit = PricingUnit.PER_ITEM,
            tags = emptySet(),
            category = category,
            brand = brand
        )
    }
}
