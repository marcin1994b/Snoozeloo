package org.marcin1994b.snoozeloo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.marcin1994b.snoozeloo.model.RepeatOn

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val time: LocalDateTime,
    val isOn: Boolean,
    val ringtoneName: String?,
    val volume: Float,
    val shouldVibrate: Boolean,
    val repeatOn: RepeatOn
) {
    companion object {
        val mock = Alarm(
            id = 0,
            name = "Default",
            time = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            isOn = true,
            ringtoneName = null,
            volume = 1.0f,
            shouldVibrate = true,
            repeatOn = RepeatOn()
        )
    }
}
