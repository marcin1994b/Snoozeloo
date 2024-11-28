package org.marcin1994b.snoozeloo.alarmScheduler

import org.marcin1994b.snoozeloo.db.Alarm

interface AlarmScheduler {
    fun schedule(alarm: Alarm)
    fun cancel(alarm: Alarm)
    fun snooze(alarm: Alarm)
}