package com.example.myshop.features.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.usecase.ObserveDeliveryAddressesUseCase
import com.example.myshop.domain.order.model.FulfillmentSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val observeAddressesUseCase: ObserveDeliveryAddressesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutUiState())
    val state = _state.asStateFlow()
    private var observeJob: Job? = null

    init {
        observeAddresses()
    }

    fun retry() {
        _state.value = _state.value.copy(isLoading = true, hasError = false)
        observeAddresses()
    }

    fun select(selection: FulfillmentSelection) {
        val current = _state.value
        if (current.isLoading || current.hasError) return
        if (selection is FulfillmentSelection.Delivery &&
            current.addresses.none { it.id == selection.addressId }
        ) return

        _state.value = current.copy(selection = selection)
    }

    private fun observeAddresses() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            observeAddressesUseCase()
                .catch { error ->
                    if (error is CancellationException) throw error
                    _state.value = _state.value.copy(isLoading = false, hasError = true)
                }
                .collect { addresses ->
                    val current = _state.value
                    val selection = when (val selected = current.selection) {
                        null -> defaultSelection(addresses)
                        FulfillmentSelection.Pickup -> selected
                        is FulfillmentSelection.Delivery -> {
                            if (addresses.any { it.id == selected.addressId }) selected
                            else defaultSelection(addresses)
                        }
                    }
                    _state.value = CheckoutUiState(
                        addresses = addresses,
                        selection = selection,
                        isLoading = false
                    )
                }
        }
    }

    private fun defaultSelection(addresses: List<DeliveryAddress>): FulfillmentSelection {
        val address = addresses.firstOrNull { it.isDefault } ?: addresses.firstOrNull()
        return address?.let { FulfillmentSelection.Delivery(it.id) }
            ?: FulfillmentSelection.Pickup
    }
}
