package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.CartTotals
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.repository.ProductRepository
import javax.inject.Inject
class CalculateCartTotalsUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val linePriceCalculator: LinePriceCalculator
) {

    suspend fun execute(): CartTotals {
        val cart = cartRepository.getCart()

        val lineTotalsCents = mutableMapOf<String, Long>()
        var totalCents = 0L

        for (item in cart.items) {
            val product = productRepository.getById(item.productId) ?: continue

            val lineCents = linePriceCalculator.calculateLineCents(
                priceCents = product.price.cents,
                pricingUnit = product.pricingUnit,
                amount = item.amount
            )

            lineTotalsCents[item.productId] = lineCents
            totalCents += lineCents
        }

        val currency = Currency.USD

        return CartTotals(
            lineTotals = lineTotalsCents.mapValues { Money(it.value, currency) },
            total = Money(totalCents, currency)
        )
    }
}
