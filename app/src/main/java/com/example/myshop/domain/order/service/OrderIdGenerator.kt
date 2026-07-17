package com.example.myshop.domain.order.service

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random


class OrderIdGenerator @Inject constructor() {

    operator fun invoke(): String {
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
        val randomNum = Random.nextInt(10000,99999)
        return "ORD-$date-$randomNum"
    }
}