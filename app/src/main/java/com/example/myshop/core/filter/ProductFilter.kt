package com.example.myshop.core.filter

import com.example.myshop.domain.product.model.Product
import javax.inject.Inject

class ProductFilter @Inject constructor() {

    fun apply(
        products: List<Product>,
        filterParams: FilterParams
    ): List<Product> {
        val filteredByCategories = if (filterParams.categories.isEmpty()) {
            products
        } else {
            products.filter { product ->
                product.category in filterParams.categories
            }
        }

        val filteredByBrands = if (filterParams.brands.isEmpty()) {
            filteredByCategories
        } else {
            filteredByCategories.filter { product ->
                product.brand in filterParams.brands
            }
        }

        return when (filterParams.priceSort) {
            PriceSort.LOW_TO_HIGH -> filteredByBrands.sortedBy { it.price.cents }
            PriceSort.HIGH_TO_LOW -> filteredByBrands.sortedByDescending { it.price.cents }
            null -> filteredByBrands
        }
    }
}
