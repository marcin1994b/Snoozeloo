package org.marcin1994b.snoozeloo.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun Long.formatToAlarmTime(): String {
    val localTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

    val formattedHour = if (localTime.hour in (0..9)) {
        "0${localTime.hour}"
    } else {
        "${localTime.hour}"
    }

    val formattedMinutes = if (localTime.minute in (0..9)) {
        "0${localTime.minute}"
    } else {
        "${localTime.minute}"
    }

    return "${formattedHour}:${formattedMinutes}"
}

fun Long.getAlarmCountDownText(): String {
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val localAlarmTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

    val hoursToAlarm = currentTime.hour.minus(localAlarmTime.hour).hours.absoluteValue.inWholeHours
    val minutesToAlarm = currentTime.minute.minus(localAlarmTime.minute).minutes.absoluteValue.inWholeMinutes

    var time = if (hoursToAlarm != 0L) {
        "$hoursToAlarm h"
    } else {
        ""
    }

    time = "$time " + if (minutesToAlarm != 0L) {
        "$minutesToAlarm mins"
    } else {
        ""
    }

    return "Alarm in $time"
}