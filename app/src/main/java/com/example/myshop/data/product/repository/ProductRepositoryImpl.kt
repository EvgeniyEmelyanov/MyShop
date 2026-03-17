package com.example.myshop.data.product.repository

import com.example.myshop.data.product.model.ProductUnit
import com.example.myshop.data.product.mapper.ProductPricingMapper
import com.example.myshop.data.product.datasource.ProductStore
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import java.math.BigDecimal
import java.math.RoundingMode

class ProductRepositoryImpl : ProductRepository {


    override fun getAllProducts(): List<Product> {
        return ProductStore.allProducts.map { rawProduct ->
            rawProduct.toDomain()
        }
    }

    override fun getById(id: String): Product? {
        val rawProduct = ProductStore.findById(id) ?: return null
        return rawProduct.toDomain()
    }

    private fun com.example.myshop.data.product.model.Product.toDomain(): Product {
        return Product(
            id = id,
            title = title,
            subtitle = weight,
            description = productDescription,
            imageKey = imageKey,
            price = parseMoney(price),
            amountType = unit.toAmountType(),
            pricingUnit = ProductPricingMapper.fromWeight(weight),
            tags = tags
        )
    }

    private fun ProductUnit.toAmountType(): AmountType =
        when (this) {
            ProductUnit.PIECE -> AmountType.PIECE
            ProductUnit.GRAM -> AmountType.WEIGHT
        }

    private fun parseMoney(raw: String): Money {
        val normalized = raw.trim().replace(Regex("[^0-9.]"), "")
        val cents = BigDecimal(normalized)
            .multiply(BigDecimal(100))
            .setScale(0, RoundingMode.HALF_UP)
            .toLong()

        return Money(cents = cents, currency = Currency.USD)
    }

}