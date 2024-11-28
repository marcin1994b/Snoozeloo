package org.marcin1994b.snoozeloo.schedulers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.marcin1994b.snoozeloo.ACTION_SHOW_NOTIFICATION
import org.marcin1994b.snoozeloo.EXTRAS_ALARM_ID
import org.marcin1994b.snoozeloo.alarmScheduler.AlarmScheduler
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.model.getAlarmDateTime
import org.marcin1994b.snoozeloo.receivers.AlarmReceiver

class AndroidAlarmScheduler(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: Alarm) {
        scheduleAlarm(alarm, 0)
    }

    override fun cancel(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun snooze(alarm: Alarm) {
        scheduleAlarm(alarm, 10)
    }

    private fun scheduleAlarm(alarm: Alarm, delayInMinutes: Int) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_SHOW_NOTIFICATION
            putExtra(EXTRAS_ALARM_ID, alarm.id.toString())
        }

        val alarmDuration = getAlarmDateTime(
            alarmHour = alarm.alarmHour,
            alarmMinute = alarm.alarmMinute-1 + delayInMinutes,
            repeatOn = alarm.repeatOn
        )
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmDuration,
            PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}