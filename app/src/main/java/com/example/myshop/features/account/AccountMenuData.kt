package com.example.myshop.features.account

import com.example.myshop.R


object AccountMenuData {
    const val ORDERS = 1
    const val MY_DETAILS = 2
    const val DELIVERY_ADDRESS = 3
    const val PAYMENT_METHODS = 4
    const val PROMO_CODE = 5
    const val NOTIFICATIONS = 6
    const val HELP = 7
    const val ABOUT = 8

    fun defaultAccountMenuItems(): List<AccountMenuItem> {
        return listOf(
            AccountMenuItem(ORDERS, R.string.order, R.drawable.ic_orders),
            AccountMenuItem(MY_DETAILS, R.string.my_details, R.drawable.ic_my_details),
            AccountMenuItem(DELIVERY_ADDRESS, R.string.delivery_address, R.drawable.ic_delivery_address),
            AccountMenuItem(PAYMENT_METHODS, R.string.payment_methods, R.drawable.ic_payment),
            AccountMenuItem(PROMO_CODE, R.string.promo_code, R.drawable.ic_promo_cord),
            AccountMenuItem(NOTIFICATIONS, R.string.notifications, R.drawable.ic_bell),
            AccountMenuItem(HELP, R.string.help, R.drawable.ic_help),
            AccountMenuItem(ABOUT, R.string.about, R.drawable.ic_about)
        )
    }
}