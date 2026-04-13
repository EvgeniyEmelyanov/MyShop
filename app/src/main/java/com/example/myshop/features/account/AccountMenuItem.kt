package com.example.myshop.features.account

import androidx.annotation.StringRes

data class AccountMenuItem(
    val id: Int,
    @StringRes val title: Int,
    val iconRes: Int
)