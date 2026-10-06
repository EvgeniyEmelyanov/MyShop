package com.example.myshop.domain.deliveryAddress.usecase

import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    suspend operator fun invoke(address: DeliveryAddress) {
        deliveryRepository.saveAddress(address)
    }
}