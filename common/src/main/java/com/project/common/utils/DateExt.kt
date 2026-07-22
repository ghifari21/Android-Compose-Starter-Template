package com.project.common.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

/**
 * Formats a timestamp (Long) to a string representation based on the given pattern.
 */
fun Long.formatTo(pattern: String, locale: Locale = Locale.getDefault()): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formatter)
}

/**
 * Checks if the timestamp (Long) represents today.
 */
fun Long.isToday(): Boolean {
    val date = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    return date == LocalDate.now()
}

/**
 * Converts a string date to a timestamp (Long) based on the given pattern.
 */
fun String.toTimestamp(pattern: String, locale: Locale = Locale.getDefault()): Long {
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    val localDateTime = LocalDateTime.parse(this, formatter)
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

/**
 * Returns a human-readable "time ago" string for the timestamp (Long).
 */
fun Long.toTimeAgo(): String {
    val now = Instant.now()
    val past = Instant.ofEpochMilli(this)
    val seconds = ChronoUnit.SECONDS.between(past, now)
    val minutes = ChronoUnit.MINUTES.between(past, now)
    val hours = ChronoUnit.HOURS.between(past, now)
    val days = ChronoUnit.DAYS.between(past, now)

    return when {
        seconds < 60 -> "just now"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 7 -> "$days days ago"
        else -> this.formatTo("dd MMM yyyy")
    }
}
