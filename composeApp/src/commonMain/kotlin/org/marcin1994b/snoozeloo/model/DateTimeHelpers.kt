package org.marcin1994b.snoozeloo.model

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun getAlarmDateTime(alarmHour: Int, alarmMinute: Int, repeatOn: RepeatOn): LocalDateTime {
    val currentTime = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
        .toLocalDateTime(TimeZone.currentSystemDefault())

    var daysUntil = 0

    if (repeatOn.days.any { it.second }) {
        val currentDayOfWeek = currentTime.dayOfWeek.isoDayNumber
        var numberOfNextRepeatDay = 0

        for (index in (currentDayOfWeek+1..currentDayOfWeek+7)) {

            if (repeatOn.days[(index-1).mod(7)].second) {
                numberOfNextRepeatDay = index
                break
            }
        }

        daysUntil = if (repeatOn.days[currentDayOfWeek-1].second && ((alarmHour == currentTime.hour && alarmMinute > currentTime.minute) || (alarmHour > currentTime.hour))) {
            0
        } else if (numberOfNextRepeatDay < currentDayOfWeek) {
            numberOfNextRepeatDay - currentDayOfWeek
        } else if (numberOfNextRepeatDay > currentDayOfWeek) {
            numberOfNextRepeatDay - currentDayOfWeek
        } else {
            7
        }

    } else if ((alarmHour == currentTime.hour && alarmMinute < currentTime.minute) || (alarmHour < currentTime.hour)) {
        daysUntil = 1
    } else if (currentTime.hour == alarmHour && currentTime.minute == alarmMinute) {
        daysUntil = 1
    }

    return LocalDateTime(
        date = currentTime.date.plus(DatePeriod(0,0, daysUntil)),
        time = LocalTime(
            hour = alarmHour,
            minute = alarmMinute+1
        )
    )
}

fun getAlarmDuration(alarmHour: Int, alarmMinute: Int, repeatOn: RepeatOn): Duration {

    val alarmDuration = getAlarmDateTime(alarmHour, alarmMinute, repeatOn)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
        .toDuration(DurationUnit.MILLISECONDS)

    val currentTimeDuration = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
        .toEpochMilliseconds()
        .toDuration(DurationUnit.MILLISECONDS)

    return alarmDuration.minus(currentTimeDuration)
}

fun getAlarmInText(alarmHour: Int, alarmMinute: Int, repeatOn: RepeatOn): String {
    val alarmDuration = getAlarmDuration(alarmHour, alarmMinute, repeatOn)

    val daysToAlarm = if (alarmDuration.inWholeDays == 0L) "" else "${alarmDuration.inWholeDays}d"
    val hoursToAlarm = if (alarmDuration.inWholeHours.mod(24) == 0) "" else "${alarmDuration.inWholeHours.mod(24)}h"
    val minutesToAlarm = if (alarmDuration.inWholeMinutes.mod(60) == 0) "" else "${(alarmDuration.inWholeMinutes).mod(60)}m"

    return buildString {
        if (daysToAlarm.isNotEmpty()) {
            append("$daysToAlarm ")
        }

        if (hoursToAlarm.isNotEmpty()) {
            append("$hoursToAlarm ")
        }

        if (minutesToAlarm.isNotEmpty()) {
            append("$minutesToAlarm ")
        }
    }
}