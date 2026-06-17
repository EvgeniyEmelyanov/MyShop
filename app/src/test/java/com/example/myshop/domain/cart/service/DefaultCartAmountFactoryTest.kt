package com.example.myshop.domain.cart.service

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.product.model.AmountType
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultCartAmountFactoryTest {

    private val factory = DefaultCartAmountFactory()

    @Test
    fun `invoke returns one piece for piece product`() {
        val result = factory(AmountType.PIECE)

        assertEquals(Amount.Piece(1), result)
    }

    @Test
    fun `invoke returns one kilogram for weight product`() {
        val result = factory(AmountType.WEIGHT)

        assertEquals(Amount.Grams(1000), result)
    }
}
