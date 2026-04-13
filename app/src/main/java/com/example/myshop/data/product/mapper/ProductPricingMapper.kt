package com.example.myshop.data.product.mapper

import com.example.myshop.domain.product.model.PricingUnit

object ProductPricingMapper {
    fun fromWeight(weight: String): PricingUnit {
        return if (weight.contains("Price/kg", ignoreCase = true)) {
            PricingUnit.PER_KG
        } else {
            PricingUnit.PER_ITEM
        }
    }
}