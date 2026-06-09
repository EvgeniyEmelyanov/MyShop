package com.example.myshop.core.filter

import android.os.Parcelable
import com.example.myshop.domain.product.model.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterParams(
    val categories: Set<Category> = emptySet(),
    val brands: Set<Brands> = emptySet(),
    val priceSort: PriceSort? = null
): Parcelable


enum class Brands(val displayName: String) {
    INDIVIDUAL_COLLECTION("Individual Collection"),
    COCACOOLA("CocaCola"),
    IFAAD("Ifad"),
    KAZI_FARMS("Kazi Farms")
}

enum class PriceSort(val displayName: String) {
    LOW_TO_HIGH("Low to high"),
    HIGH_TO_LOW("High to low")
}
