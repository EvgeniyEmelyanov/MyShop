package com.example.myshop.features.deliveryAddress.presentation

import com.example.myshop.domain.deliveryAddress.DeliveryAddressType

data class DeliveryAddressEditorUiState(
    val addressId: Long = 0,
    val isEditing: Boolean = false,
    val type: DeliveryAddressType = DeliveryAddressType.HOME,
    val settlement: String = "",
    val street: String = "",
    val house: String = "",
    val building: String = "",
    val apartment: String = "",
    val isDefault: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val showValidationErrors: Boolean = false,
    val loadFailed: Boolean = false,
    val saveFailed: Boolean = false
) {
    val isFormValid: Boolean
        get() = settlement.isNotBlank() && street.isNotBlank() && house.isNotBlank()
}
