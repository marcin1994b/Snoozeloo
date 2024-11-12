package org.marcin1994b.snoozeloo.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.formatToAlarmTime(): String {
    val localTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

    return "${localTime.hour}:${localTime.minute}"
}

fun Long.getAlarmCountDownText(): String {
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val localAlarmTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

    val hoursToAlarm = localAlarmTime.hour - currentTime.hour
    val minutesToAlarm = localAlarmTime.minute - currentTime.minute

    var time = if (hoursToAlarm > 0) {
        "$hoursToAlarm h"
    } else {
        ""
    }

    time = "$time " + if (minutesToAlarm > 0) {
        "$minutesToAlarm mins"
    } else {
        ""
    }

    return "Alarm in $time"
}