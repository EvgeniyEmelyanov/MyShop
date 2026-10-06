package com.example.myshop.data.deliveryAddress

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class DeliveryAddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val settlement: String,
    val street: String,
    val house: String,
    val building: String?,
    val apartment: String?,
    val isDefault: Boolean
)
