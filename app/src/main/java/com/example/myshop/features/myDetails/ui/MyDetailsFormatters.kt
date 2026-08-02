package com.example.myshop.features.myDetails.ui

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val Gender.label: String
    get() = when (this) {
        Gender.MALE -> "Male"
        Gender.FEMALE -> "Female"
        Gender.OTHER -> "Other"
    }

fun String.toUtcMillisOrNull(): Long? {
    return runCatching {
        LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }.getOrNull()
}

fun String.toDisplayBirthDate(): String {
    return runCatching {
        LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }.getOrDefault(this)
}

fun String.toDisplayGender(): String {
    return Gender.entries
        .firstOrNull { gender -> this == gender.name || this == gender.label }
        ?.label
        ?: this
}

fun Long.toIsoLocalDate(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE)
}
