package com.example.myshop.features.checkout

import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.deliveryAddress.usecase.ObserveDeliveryAddressesUseCase
import com.example.myshop.domain.order.model.FulfillmentSelection
import com.example.myshop.testutil.FakeDeliveryRepository
import com.example.myshop.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CheckoutViewModelTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `selects default address after load and keeps manual pickup`() = runTest(mainDispatcherRule.dispatcher) {
        val repository = FakeDeliveryRepository(listOf(address(1), address(2, isDefault = true)))
        val viewModel = CheckoutViewModel(ObserveDeliveryAddressesUseCase(repository))
        advanceUntilIdle()

        assertEquals(FulfillmentSelection.Delivery(2), viewModel.state.value.selection)
        viewModel.select(FulfillmentSelection.Pickup)
        assertEquals(FulfillmentSelection.Pickup, viewModel.state.value.selection)
        repository.emit(listOf(address(1), address(2, isDefault = true), address(3)))
        advanceUntilIdle()

        assertEquals(FulfillmentSelection.Pickup, viewModel.state.value.selection)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `removed selected address falls back to available default`() = runTest(mainDispatcherRule.dispatcher) {
        val repository = FakeDeliveryRepository(listOf(address(1, isDefault = true), address(2)))
        val viewModel = CheckoutViewModel(ObserveDeliveryAddressesUseCase(repository))
        advanceUntilIdle()

        viewModel.select(FulfillmentSelection.Delivery(2))
        repository.emit(listOf(address(1, isDefault = true)))
        advanceUntilIdle()

        assertEquals(FulfillmentSelection.Delivery(1), viewModel.state.value.selection)
    }

    @Test
    fun `empty address list selects pickup`() = runTest(mainDispatcherRule.dispatcher) {
        val viewModel = CheckoutViewModel(ObserveDeliveryAddressesUseCase(FakeDeliveryRepository()))
        advanceUntilIdle()

        assertEquals(FulfillmentSelection.Pickup, viewModel.state.value.selection)
    }

    private fun address(id: Long, isDefault: Boolean = false) = DeliveryAddress(
        id = id, type = DeliveryAddressType.HOME, settlement = "Minsk",
        street = "Central", house = "2", building = null, apartment = null,
        isDefault = isDefault
    )
}
