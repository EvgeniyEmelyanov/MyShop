package com.example.myshop.features.deliveryAddress.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.deliveryAddress.usecase.GetAddressByIdUseCase
import com.example.myshop.domain.deliveryAddress.usecase.SaveAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryAddressEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAddressByIdUseCase: GetAddressByIdUseCase,
    private val saveAddressUseCase: SaveAddressUseCase
) : ViewModel() {

    private val requestedAddressId =
        savedStateHandle.get<Long>(ADDRESS_ID_ARGUMENT) ?: NEW_ADDRESS_ID

    private val _state = MutableStateFlow(
        DeliveryAddressEditorUiState(isLoading = requestedAddressId != NEW_ADDRESS_ID)
    )
    val state = _state.asStateFlow()

    private val _addressSavedEvent = MutableSharedFlow<Unit>()
    val addressSavedEvent = _addressSavedEvent.asSharedFlow()

    init {
        if (requestedAddressId != NEW_ADDRESS_ID) {
            loadAddress()
        }
    }

    fun onTypeChanged(type: DeliveryAddressType) {
        _state.update { it.copy(type = type, saveFailed = false) }
    }

    fun onSettlementChanged(value: String) {
        _state.update { it.copy(settlement = value, saveFailed = false) }
    }

    fun onStreetChanged(value: String) {
        _state.update { it.copy(street = value, saveFailed = false) }
    }

    fun onHouseChanged(value: String) {
        _state.update { it.copy(house = value, saveFailed = false) }
    }

    fun onBuildingChanged(value: String) {
        _state.update { it.copy(building = value, saveFailed = false) }
    }

    fun onApartmentChanged(value: String) {
        _state.update { it.copy(apartment = value, saveFailed = false) }
    }

    fun onSaveClick() {
        val currentState = _state.value

        if (currentState.isSaving) return

        if (!currentState.isFormValid) {
            _state.update { it.copy(showValidationErrors = true) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, saveFailed = false) }

            try {
                saveAddressUseCase(
                    DeliveryAddress(
                        id = currentState.addressId,
                        type = currentState.type,
                        settlement = currentState.settlement.trim(),
                        street = currentState.street.trim(),
                        house = currentState.house.trim(),
                        building = currentState.building.trim().ifEmpty { null },
                        apartment = currentState.apartment.trim().ifEmpty { null },
                        isDefault = currentState.isDefault
                    )
                )
                _addressSavedEvent.emit(Unit)
            } catch (error: Exception) {
                if (error is CancellationException) throw error

                _state.update {
                    it.copy(
                        isSaving = false,
                        saveFailed = true
                    )
                }
            }
        }
    }

    fun retryLoading() {
        loadAddress()
    }

    private fun loadAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, loadFailed = false) }

            try {
                val address = getAddressByIdUseCase(requestedAddressId)

                if (address == null) {
                    _state.update { it.copy(isLoading = false, loadFailed = true) }
                    return@launch
                }

                _state.value = DeliveryAddressEditorUiState(
                    addressId = address.id,
                    isEditing = true,
                    type = address.type,
                    settlement = address.settlement,
                    street = address.street,
                    house = address.house,
                    building = address.building.orEmpty(),
                    apartment = address.apartment.orEmpty(),
                    isDefault = address.isDefault
                )
            } catch (error: Exception) {
                if (error is CancellationException) throw error

                _state.update { it.copy(isLoading = false, loadFailed = true) }
            }
        }
    }

    companion object {
        const val ADDRESS_ID_ARGUMENT = "addressId"
        const val NEW_ADDRESS_ID = -1L
    }
}
