package org.marcin1994b.snoozeloo.model

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun DayOfWeek.toText() = when (this) {
    DayOfWeek.MONDAY -> "Mo"
    DayOfWeek.TUESDAY -> "Tu"
    DayOfWeek.WEDNESDAY -> "We"
    DayOfWeek.THURSDAY -> "Th"
    DayOfWeek.FRIDAY -> "Fr"
    DayOfWeek.SATURDAY -> "Sa"
    DayOfWeek.SUNDAY -> "Su"
}