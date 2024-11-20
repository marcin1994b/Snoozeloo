package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.runtime.mutableStateOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.marcin1994b.snoozeloo.db.AlarmEntity
import org.marcin1994b.snoozeloo.db.AppDatabase
import org.marcin1994b.snoozeloo.model.RepeatOn
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SetAlarmViewModel(
    private val database: AppDatabase
) {

    val alarmData = mutableStateOf(
        AlarmEntity(
            id = Uuid.random().toString(),
            name = "Default",
            time = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            isOn = true,
            ringtoneName = null,
            volume = 1.0f,
            shouldVibrate = true,
            repeatOn = RepeatOn()
        )
    )

    fun saveAlarm(
        hour: Int,
        minute: Int,
        alarmName: String,
        ringtone: String,
        vibrate: Boolean,
        volume: Float,
        repeatOn: RepeatOn
    ) {
        val newTime = LocalDateTime(
            date = alarmData.value.time.date, // probably should add 1 day if past midnight
            time = LocalTime(hour, minute, 0, 0)
        )

        val newAlarm = AlarmEntity(
            id = Uuid.random().toString(),
            name = alarmName,
            time = newTime,
            isOn = true,
            ringtoneName = ringtone,
            volume = volume,
            shouldVibrate = vibrate,
            repeatOn = repeatOn
        )

        println("Alarm -> ${newAlarm.toString()}")
    }

}