package com.example.myshop.data.product.repository

import com.example.myshop.ProductUnit
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

    override fun getById(id: String): Product? {
        val p = ProductStore.findById(id) ?: return null

        return Product(
            id = p.id,
            title = p.title,
            subtitle = p.weight,
            imageKey = p.imageKey,
            price = parseMoney(p.price),
            amountType = p.unit.toAmountType(),
            pricingUnit = ProductPricingMapper.fromWeight(p.weight),
            description = p.productDescription
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