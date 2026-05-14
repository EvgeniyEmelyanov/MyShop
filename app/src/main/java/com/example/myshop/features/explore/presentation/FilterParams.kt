package com.example.myshop.features.explore.presentation

import android.os.Parcelable
import com.example.myshop.domain.product.model.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterParams(
    val categories: Set<Category> = emptySet(),
    val brands: Set<Brands> = emptySet(),
    val priceRange: PriceRange? = null
): Parcelable


enum class Brands {
    COCOOLA,
    IFAAD,
    KAZI_FARMAS
}
