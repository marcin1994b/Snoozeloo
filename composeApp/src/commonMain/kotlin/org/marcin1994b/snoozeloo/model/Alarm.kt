package org.marcin1994b.snoozeloo.model

import kotlinx.datetime.LocalDateTime


data class Alarm(
    val id: String,
    val name: String?,
    val time: LocalDateTime,
    val isOn: Boolean,
    val ringtoneName: String?,
    val volume: Float,
    val shouldVibrate: Boolean,
    val repeatOn: RepeatOn
)
