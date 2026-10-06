package com.example.myshop.data.deliveryAddress

import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType

fun DeliveryAddress.toEntity(): DeliveryAddressEntity {
    return DeliveryAddressEntity(
        id = id,
        type = type.name,
        settlement = settlement,
        street = street,
        house = house,
        building = building,
        apartment = apartment,
        isDefault = isDefault
    )
}

fun DeliveryAddressEntity.toDomain(): DeliveryAddress {
    return DeliveryAddress(
        id = id,
        type = DeliveryAddressType.valueOf(type),
        settlement = settlement,
        street = street,
        house = house,
        building = building,
        apartment = apartment,
        isDefault = isDefault
    )
}
