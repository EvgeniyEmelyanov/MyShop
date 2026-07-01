package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveCartUseCaseTest {

    @Test
    fun `observe cart forwards repository updates`() = runBlocking {
        val initialCart = Cart()
        val updatedCart = Cart(
            items = listOf(
                CartItem(productId = "apple", amount = Amount.Piece(1))
            )
        )
        val repository = FakeCartRepository(initialCart)
        val useCase = ObserveCartUseCase(repository)

        val emissions = async(start = CoroutineStart.UNDISPATCHED) {
            useCase().take(2).toList()
        }

        repository.emit(updatedCart)

        assertEquals(listOf(initialCart, updatedCart), emissions.await())
    }

    private class FakeCartRepository(initialCart: Cart) : CartRepository {
        private val cartFlow = MutableStateFlow(initialCart)

        fun emit(cart: Cart) {
            cartFlow.value = cart
        }

        override suspend fun getCart(): Cart = cartFlow.value

        override fun observeCart(): Flow<Cart> = cartFlow

        override suspend fun addToCart(productId: String, amount: Amount) = Unit

        override suspend fun removeProduct(productId: String) = Unit

        override suspend fun clearProducts() = Unit

        override suspend fun setAmount(productId: String, amount: Amount) = Unit
    }
}
