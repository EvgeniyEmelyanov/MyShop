package com.example.myshop

import androidx.annotation.StringRes

data class AccountMenuItem(
    val id: Int,
    @StringRes val title: Int,
    val iconRes: Int
)