package com.example.myshop.core.ui.formatter

import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
class MoneyFormatter @Inject constructor() {

    fun format(money: Money): String {
        val amount = BigDecimal(money.cents)
            .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)


        val currencySymbol = when (money.currency) {
            Currency.BYN -> "руб."
            Currency.USD -> "$"
        }

        return "$amount $currencySymbol"
    }
}
