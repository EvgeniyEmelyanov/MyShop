package com.example.myshop.features.explore.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceRange(
    val min: Double?,
    val max: Double?
): Parcelable
