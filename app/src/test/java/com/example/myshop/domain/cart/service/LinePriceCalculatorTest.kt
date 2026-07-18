package com.example.myshop.domain.cart.service

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.product.model.PricingUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LinePriceCalculatorTest {

    private val calculator = LinePriceCalculator()

    @Test
    fun `calculateLineCents returns total for item priced product`() {
        val result = calculator(
            priceCents = 499,
            pricingUnit = PricingUnit.PER_ITEM,
            amount = Amount.Piece(3)
        )

        assertEquals(1497, result)
    }

    @Test
    fun `calculateLineCents returns total for kg priced product`() {
        val result = calculator(
            priceCents = 499,
            pricingUnit = PricingUnit.PER_KG,
            amount = Amount.Grams(500)
        )

        assertEquals(249, result)
    }

    @Test
    fun `calculateLineCents returns zero when item product receives grams amount`() {
        val result = calculator(
            priceCents = 499,
            pricingUnit = PricingUnit.PER_ITEM,
            amount = Amount.Grams(500)
        )

        assertEquals(0, result)
    }

    @Test
    fun `calculateLineCents returns zero when kg product receives piece amount`() {
        val result = calculator(
            priceCents = 499,
            pricingUnit = PricingUnit.PER_KG,
            amount = Amount.Piece(3)
        )

        assertEquals(0, result)
    }

    @Test
    fun `piece amount cannot be zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            Amount.Piece(0)
        }
    }

    @Test
    fun `grams amount cannot be zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            Amount.Grams(0)
        }
    }
}
