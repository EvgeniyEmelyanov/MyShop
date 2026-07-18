package com.example.myshop.core.formatter

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class DateFormatter @Inject constructor() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    fun formatDate(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = instant.atZone(ZoneId.systemDefault())
        return dateTime.format(dateFormatter)
    }

    fun formatTime(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = instant.atZone(ZoneId.systemDefault())
        return dateTime.format(timeFormatter)
    }

}