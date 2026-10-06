package com.example.myshop.domain.deliveryAddress

import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {
    fun observeAddresses(): Flow<List<DeliveryAddress>>
    suspend fun getAddressById(id: Long): DeliveryAddress?
    suspend fun saveAddress(address: DeliveryAddress)
    suspend fun deleteAddress(id: Long)
    suspend fun setDefaultAddress(id: Long)
}