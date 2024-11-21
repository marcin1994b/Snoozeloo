package org.marcin1994b.snoozeloo.db

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import org.marcin1994b.snoozeloo.model.RepeatOn

class Converters {

    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime): String {
        return Json.encodeToString(LocalDateTime.serializer(), value)
    }

    @TypeConverter
    fun fromStringToLocalDateTime(value: String): LocalDateTime {
        return Json.decodeFromString(LocalDateTime.serializer(), value)
    }

    @TypeConverter
    fun fromRepeatOnToString(value: RepeatOn): String {
        return Json.encodeToString(RepeatOn.serializer(), value)
    }

    @TypeConverter
    fun fromStringToRepeatOn(value: String): RepeatOn {
        return Json.decodeFromString(RepeatOn.serializer(), value)
    }
}