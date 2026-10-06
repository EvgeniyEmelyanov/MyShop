package com.example.myshop.data.deliveryAddress

import androidx.room.withTransaction
import com.example.myshop.data.local.AppDatabase
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeliveryAddressRepositoryImpl @Inject constructor(
    private val deliveryAddressDao: DeliveryAddressDao,
    private val dataBase: AppDatabase
) : DeliveryRepository {

    override fun observeAddresses(): Flow<List<DeliveryAddress>> {
        return deliveryAddressDao.observeAll().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun getAddressById(id: Long): DeliveryAddress? {
        return deliveryAddressDao.getById(id)?.toDomain()
    }

    override suspend fun saveAddress(address: DeliveryAddress) {
        dataBase.withTransaction {
            val existingAddress = deliveryAddressDao.getById(address.id)
            val shouldBeDefault = deliveryAddressDao.countAddresses() == 0 ||
                address.isDefault ||
                existingAddress?.isDefault == true
            val entityToSave = address.toEntity().copy(isDefault = shouldBeDefault)

            if (entityToSave.isDefault) {
                deliveryAddressDao.clearDefault()
            }

            deliveryAddressDao.upsert(entityToSave)
        }
    }

    override suspend fun deleteAddress(id: Long) {
        dataBase.withTransaction {
            val wasDefault = deliveryAddressDao.getById(id)?.isDefault == true

            deliveryAddressDao.delete(id)

            if (wasDefault) {
                val nextAddress = deliveryAddressDao.getFirstAddress()
                if (nextAddress != null) {
                    deliveryAddressDao.setDefaultAddress(nextAddress.id)
                }
            }
        }
    }

    override suspend fun setDefaultAddress(id: Long) {
        dataBase.withTransaction {
            val addressToSetDefault = deliveryAddressDao.getById(id)

            if (addressToSetDefault != null) {
                deliveryAddressDao.setDefaultAddress(id)
            }
        }
    }
}
