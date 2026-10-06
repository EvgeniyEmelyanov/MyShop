package com.example.myshop.features.deliveryAddress.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.usecase.DeleteAddressUseCase
import com.example.myshop.domain.deliveryAddress.usecase.ObserveDeliveryAddressesUseCase
import com.example.myshop.domain.deliveryAddress.usecase.SetDefaultAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryAddressViewModel @Inject constructor(
    private val observeAddressesUseCase: ObserveDeliveryAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val setDefaultAddressUseCase: SetDefaultAddressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DeliveryAddressUiState())
    val state = _state.asStateFlow()
    private var observeAddressesJob: Job? = null

    init {
        observeAddresses()
    }

    fun deleteAddress(id: Long) {
        viewModelScope.launch {
            deleteAddressUseCase(id)
        }
    }

    fun setDefaultAddress(id: Long) {
        viewModelScope.launch {
            setDefaultAddressUseCase(id)
        }
    }

    private fun observeAddresses() {
        observeAddressesJob?.cancel()

        observeAddressesJob = viewModelScope.launch {
            observeAddressesUseCase()
                .catch { error ->
                    if (error is CancellationException) throw error

                    _state.value = _state.value.copy(
                        contentState = ContentState.ERROR
                    )
                }
                .collect { addresses ->
                    updateState(addresses)
                }
        }
    }

    private suspend fun updateState(addresses: List<DeliveryAddress>) {
        val currentState = _state.value

        try {
            val newState = buildState(addresses)

            if (newState.addresses.isEmpty()) {
                _state.value = newState.copy(contentState = ContentState.EMPTY)
            } else {
                _state.value = newState.copy(contentState = ContentState.CONTENT)
            }
        } catch (error: Exception) {
            if (error is CancellationException) {
                throw error
            }
            _state.value = currentState.copy(contentState = ContentState.ERROR)
        }
    }

    private fun buildState(
        addresses: List<DeliveryAddress>
    ): DeliveryAddressUiState {
        val uiAddresses = addresses.map { address ->
            DeliveryAddressUiModel(
                id = address.id,
                type = address.type,
                settlement = address.settlement,
                street = address.street,
                house = address.house,
                building = address.building,
                apartment = address.apartment,
                isDefault = address.isDefault
            )
        }

        return DeliveryAddressUiState(
            addresses = uiAddresses
        )
    }
}
