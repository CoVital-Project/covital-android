package org.covital.common.extensions

import org.covital.common.domain.Try
import org.covital.common.domain.model.InstantParts
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

fun localTZ(): ZoneId {
    return Try { ZoneId.systemDefault() }.or { ZoneId.of(Calendar.getInstance().timeZone.displayName) }
}

fun utc(): ZoneId {
    return ZoneOffset.UTC
}

fun Instant.toLocalDateTime(zoneId: ZoneId): LocalDateTime {
    return LocalDateTime.ofInstant(this, zoneId)
}

fun Instant.midnightUTC(): Instant {
    val local = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
    return minus(Duration.ofHours(local.hour.toLong()))
        .minus(Duration.ofMinutes(local.minute.toLong()))
        .minus(Duration.ofSeconds(local.second.toLong()))
        .minus(Duration.ofNanos(local.nano.toLong()))
}

fun Instant.midnightLocal(): LocalDateTime {
    val local = toLocalDateTime(localTZ())
    return minus(Duration.ofHours(local.hour.toLong()))
        .minus(Duration.ofMinutes(local.minute.toLong()))
        .minus(Duration.ofSeconds(local.second.toLong()))
        .minus(Duration.ofNanos(local.nano.toLong())).toLocalDateTime(localTZ())
}

fun Instant.countDaysAgo(current : Instant = Instant.now()): Int {
    return countHoursAgo(current) / 24
}

fun Instant.countHoursAgo(current : Instant = Instant.now()): Int {
    return countMinutesAgo(current) / 60
}

fun Instant.countMinutesAgo(current : Instant = Instant.now()): Int {
    return countSecondsAgo(current) / 60
}

fun Instant.countSecondsAgo(current : Instant = Instant.now()): Int {
    return countMillisecondsAgo(current) / 1000
}

fun Instant.countMillisecondsAgo(current : Instant = Instant.now()): Int {
    return (current.toEpochMilli() - this.toEpochMilli()).toInt()
}

fun Instant.daysAgo(count: Long): Instant {
    return this.minus(Duration.ofDays(count))
}

fun Instant.hoursAgo(count: Long): Instant {
    return this.minus(Duration.ofDays(count))
}

fun Instant.minutesAgo(count: Long): Instant {
    return this.minus(Duration.ofDays(count))
}

fun Instant.secondsAgo(count: Long): Instant {
    return this.minus(Duration.ofDays(count))
}

fun Instant.millisecondsAgo(count: Long): Instant {
    return this.minus(Duration.ofDays(count))
}

fun Instant.getHourMinuteAmPm(locale: Locale = Locale.US): String {
    return toLocalDateTime(utc()).format("h:mma", locale)
}

fun Instant.localFullFormatted(locale: Locale = Locale.US): String {
    return toLocalDateTime(utc()).format("E, MMM d, h:mma", locale)
}

fun Instant.localYMonthD(locale: Locale = Locale.US): String {
    return toLocalDateTime(utc()).format("E, MMM d", locale)
}

fun Instant.formatYMD(locale: Locale = Locale.getDefault()): String {
    return toLocalDateTime(utc()).format("YY/M/d", locale)
}

fun LocalDateTime.format(pattern: String, locale: Locale): String {
    return format(DateTimeFormatter.ofPattern(pattern, locale))
}

fun Long.getFriendlyTimeAgoString(current : Instant = Instant.now()): String {
    return Instant.ofEpochMilli(this * 1000).getFriendlyTimeAgoString(current)
}

/**
 * Creates a Calendar instance and sets the fields for year, month,
 * and day without retaining any other information.
 *  Month value is 0-based. e.g., 0 for January.
 */
@Throws(IllegalArgumentException::class)
fun InstantParts.parseDate(): Instant {
    if (months < 0) throw IllegalArgumentException("Month must be a positive number or zero")
    if (months >= 12) throw IllegalArgumentException("Month must be less than 12")
    if (days <= 0) throw IllegalArgumentException("Day must be a positive number")
    if (days > 31) throw IllegalArgumentException("Day must be less than or equal to 31")

    val millis = Calendar.getInstance().apply {
        clear()
        set(years, months, days)
        timeZone = TimeZone.getTimeZone("UTC")
    }.timeInMillis

    return Instant.ofEpochMilli(millis)
}

private fun Instant.diffForParts(current: Instant): InstantParts {
    var diff = current.epochSecond - this.epochSecond
    val sec: Int = (if (diff >= 60) diff % 60 else diff).toInt()
    diff /= 60
    val min: Int = (if (diff >= 60) diff % 60 else diff).toInt()
    diff /= 60
    val hrs: Int = (if (diff >= 24) diff % 24 else diff).toInt()
    diff /= 24
    val days: Int = (if (diff >= 30) diff % 30 else diff).toInt()
    diff /= 30
    val months: Int = (if (diff >= 12) diff % 12 else diff).toInt()
    diff /= 12
    val years: Int = diff.toInt()
    return InstantParts(years, months, days, hrs, min, sec)
}

fun Instant.getFriendlyDayName(now: Instant = Instant.now()): String {
    val local = toLocalDateTime(localTZ())
    val midnight = now.midnightLocal()
    val midnightYesterday = midnight.minus(Duration.ofDays(1))
    val isToday = local.isAfter(midnight)
    val isYesterday = local.isAfter(midnightYesterday)

    return when {
        isToday -> "Today"
        isYesterday -> "Yesterday"
        else -> localYMonthD()
    }
}

fun Instant.getFriendlyDateTime(now: Instant = Instant.now()): String {
    val sb = StringBuffer()
    sb.append(getFriendlyDayName(now))
    sb.append(" ")
    sb.append(getHourMinuteAmPm())

    return sb.toString()
}

fun Instant.getFriendlyTimeAgoString(current : Instant = Instant.now()): String {
    val sb = StringBuffer()
    val parts = diffForParts(current)

    if (parts.years > 0) {
        if (parts.years == 1) {
            sb.append("a year")
        } else {
            sb.append("${parts.years} years")
        }
    } else if (parts.months > 0) {
        if (parts.months == 1) {
            sb.append("a month")
        } else {
            sb.append("${parts.months} months")
        }
    } else if (parts.days > 0) {
        if (parts.days == 1) {
            sb.append("a day")
        } else {
            sb.append("${parts.days} days")
        }
    } else if (parts.hrs > 0) {
        if (parts.hrs == 1) {
            sb.append("an hour")
        } else {
            sb.append("${parts.hrs} hours")
        }
    } else if (parts.min > 0) {
        if (parts.min == 1) {
            sb.append("a minute")
        } else {
            sb.append("${parts.min} minutes")
        }
    } else {
        if (parts.sec <= 1) {
            sb.append("about a second")
        } else {
            sb.append("${parts.sec} seconds")
        }
    }

    sb.append(" ago")

    return sb.toString()
}

fun Instant.age(): Int {
    val dob = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
    return Period.between(dob.toLocalDate(), LocalDate.now()).years
}
