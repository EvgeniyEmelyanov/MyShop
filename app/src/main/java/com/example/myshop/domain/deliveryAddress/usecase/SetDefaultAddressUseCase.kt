package com.example.myshop.domain.deliveryAddress.usecase

import com.example.myshop.domain.deliveryAddress.DeliveryRepository
import javax.inject.Inject

class SetDefaultAddressUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Long) {
        deliveryRepository.setDefaultAddress(id)
    }
}
