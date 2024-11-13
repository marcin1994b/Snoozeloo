package org.marcin1994b.snoozeloo.model


data class Alarm(
    val id: String,
    val name: String?,
    val time: Long,
    val isOn: Boolean,
    val ringtoneName: String?,
    val volume: Float,
    val shouldVibrate: Boolean,
    val repeatOn: List<Pair<DayOfWeek, Boolean>>
)
