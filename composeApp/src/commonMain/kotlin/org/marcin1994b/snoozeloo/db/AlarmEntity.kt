package org.marcin1994b.snoozeloo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import org.marcin1994b.snoozeloo.model.RepeatOn

@Entity
data class AlarmEntity2(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String?,
//    val time: LocalDateTime,
    val isOn: Boolean,
    val ringtoneName: String?,
    val volume: Float,
    val shouldVibrate: Boolean,
//    val repeatOn: RepeatOn
)

data class AlarmEntity(
    val id: String,
    val name: String?,
    val time: LocalDateTime,
    val isOn: Boolean,
    val ringtoneName: String?,
    val volume: Float,
    val shouldVibrate: Boolean,
    val repeatOn: RepeatOn
)
