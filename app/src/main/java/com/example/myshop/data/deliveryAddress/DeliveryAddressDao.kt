package com.example.myshop.data.deliveryAddress

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryAddressDao {

    @Query(
        """
    SELECT * FROM addresses
    ORDER BY isDefault DESC, id ASC
    """
    )
    fun observeAll(): Flow<List<DeliveryAddressEntity>>

    @Query("SELECT * FROM addresses WHERE id = :id")
    suspend fun getById(id: Long): DeliveryAddressEntity?

    @Upsert
    suspend fun upsert(address: DeliveryAddressEntity)

    @Query("DELETE FROM addresses WHERE id = :id")
    suspend fun delete(id: Long)

    @Query(
        """
    UPDATE addresses
    SET isDefault = CASE
        WHEN id = :id THEN 1
        ELSE 0
    END
    """
    )
    suspend fun setDefaultAddress(id: Long)

    @Query("SELECT COUNT(*) FROM addresses")
    suspend fun countAddresses(): Int

    @Query("UPDATE addresses SET isDefault = 0 WHERE isDefault = 1")
    suspend fun clearDefault()

    @Query("SELECT * FROM addresses ORDER BY id ASC LIMIT 1")
    suspend fun getFirstAddress(): DeliveryAddressEntity?
}
