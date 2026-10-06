package com.example.myshop.data.order.mapper

import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.order.model.OrderFulfillment
import com.example.myshop.testutil.testOrder
import org.junit.Assert.assertEquals
import org.junit.Test

class OrderEntityMapperTest {

    @Test
    fun `pickup order survives entity round trip`() {
        val order = testOrder()

        assertEquals(order, order.toEntity().toDomain(order.items))
    }

    @Test
    fun `delivery address snapshot survives entity round trip`() {
        val order = testOrder().copy(
            fulfillment = OrderFulfillment.Delivery(
                type = DeliveryAddressType.WORK,
                settlement = "Minsk",
                street = "Central",
                house = "2",
                building = "A",
                apartment = "4"
            )
        )

        assertEquals(order, order.toEntity().toDomain(order.items))
    }
}
