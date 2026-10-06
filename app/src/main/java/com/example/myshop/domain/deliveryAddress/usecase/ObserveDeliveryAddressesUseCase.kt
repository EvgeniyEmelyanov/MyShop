package com.example.myshop.domain.deliveryAddress.usecase

import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDeliveryAddressesUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {

    operator fun invoke(): Flow<List<DeliveryAddress>> {
        return deliveryRepository.observeAddresses()
    }
}