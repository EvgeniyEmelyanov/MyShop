package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class IncreaseAmountUseCaseTest {

    @Test
    fun `increaseAmount increases amount for piece item`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(2))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = IncreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.increaseAmount("apple")

        assertEquals("apple", fakeRepository.setAmountProductId)
        assertEquals(Amount.Piece(3), fakeRepository.setAmountValue)
    }


    @Test
    fun `increaseAmount increases amount for grams item`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Grams(200))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = IncreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.increaseAmount("apple")

        assertEquals("apple", fakeRepository.setAmountProductId)
        assertEquals(Amount.Grams(220), fakeRepository.setAmountValue)
    }

    @Test
    fun `increaseAmount does nothing when item does not exist`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(1))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = IncreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.increaseAmount("banana")

        assertNull(fakeRepository.setAmountProductId)
        assertNull(fakeRepository.setAmountValue)
    }

    private class FakeRepository(private val cart: Cart) : CartRepository {
        var setAmountProductId: String? = null
        var setAmountValue: Amount? = null

        override suspend fun getCart(): Cart = cart

        override fun observeCart(): Flow<Cart> {
            return flowOf(cart)
        }

        override suspend fun addToCart(productId: String, amount: Amount) = Unit

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) {
            setAmountProductId = productId
            setAmountValue = amount
        }
    }
}




