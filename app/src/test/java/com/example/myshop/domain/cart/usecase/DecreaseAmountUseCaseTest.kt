package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DecreaseAmountUseCaseTest {

    @Test
    fun `decreaseAmount decreases amount for piece item`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(2))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = DecreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.decreaseAmount("apple")

        assertEquals("apple", fakeRepository.setAmountProductId)
        assertEquals(Amount.Piece(1), fakeRepository.setAmountValue)
    }

    @Test
    fun `decreaseAmount decreases amount for grams item`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Grams(200))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = DecreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.decreaseAmount("apple")

        assertEquals("apple", fakeRepository.setAmountProductId)
        assertEquals(Amount.Grams(180), fakeRepository.setAmountValue)
    }

    @Test
    fun `decreaseAmount removes piece item when amount is one`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(1))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = DecreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.decreaseAmount("apple")

        assertEquals("apple", fakeRepository.removedProductId)
        assertNull(fakeRepository.setAmountValue)
    }

    @Test
    fun `decreaseAmount removes grams item when amount is twenty grams`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Grams(20))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = DecreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.decreaseAmount("apple")

        assertEquals("apple", fakeRepository.removedProductId)
        assertNull(fakeRepository.setAmountValue)
    }

    @Test
    fun `decreaseAmount does nothing when item does not exist`() = runBlocking {
        val cart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(1))
            )
        )
        val fakeRepository = FakeRepository(cart)
        val useCase = DecreaseAmountUseCase(cartRepository = fakeRepository)

        useCase.decreaseAmount("banana")

        assertNull(fakeRepository.setAmountProductId)
        assertNull(fakeRepository.setAmountValue)
        assertNull(fakeRepository.removedProductId)
    }

    private class FakeRepository(private val cart: Cart) : CartRepository {

        var setAmountProductId: String? = null
        var setAmountValue: Amount? = null
        var removedProductId: String? = null

        override suspend fun getCart(): Cart = cart

        override suspend fun addToCart(productId: String, amount: Amount) = Unit

        override suspend fun removeProduct(productId: String) {
            removedProductId = productId
        }

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) {
            setAmountProductId = productId
            setAmountValue = amount
        }
    }
}

