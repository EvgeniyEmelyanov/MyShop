package com.example.myshop.domain.deliveryAddress.usecase

import com.example.myshop.domain.deliveryAddress.DeliveryRepository
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Long) {
        deliveryRepository.deleteAddress(id)
    }
}
