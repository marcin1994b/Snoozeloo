package org.marcin1994b.snoozeloo.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
data class RepeatOn(
    val days: List<Pair<DayOfWeek, Boolean>> = listOf(
        DayOfWeek.MONDAY to false,
        DayOfWeek.TUESDAY to false,
        DayOfWeek.WEDNESDAY to false,
        DayOfWeek.THURSDAY to false,
        DayOfWeek.FRIDAY to false,
        DayOfWeek.SATURDAY to false,
        DayOfWeek.SUNDAY to false,
    )
)

data class MutableRepeatOn(
    val days: List<Pair<DayOfWeek, MutableState<Boolean>>> = listOf(
        DayOfWeek.MONDAY to mutableStateOf(false),
        DayOfWeek.TUESDAY to mutableStateOf(false),
        DayOfWeek.WEDNESDAY to mutableStateOf(false),
        DayOfWeek.THURSDAY to mutableStateOf(false),
        DayOfWeek.FRIDAY to mutableStateOf(false),
        DayOfWeek.SATURDAY to mutableStateOf(false),
        DayOfWeek.SUNDAY to mutableStateOf(false),
    )
)

fun RepeatOn.toMutableRepeatOn() = MutableRepeatOn(
    days = listOf(
        DayOfWeek.MONDAY to mutableStateOf(this.days[0].second),
        DayOfWeek.TUESDAY to mutableStateOf(this.days[1].second),
        DayOfWeek.WEDNESDAY to mutableStateOf(this.days[2].second),
        DayOfWeek.THURSDAY to mutableStateOf(this.days[3].second),
        DayOfWeek.FRIDAY to mutableStateOf(this.days[4].second),
        DayOfWeek.SATURDAY to mutableStateOf(this.days[5].second),
        DayOfWeek.SUNDAY to mutableStateOf(this.days[6].second),
    )
)

fun MutableRepeatOn.toRepeatOn() = RepeatOn(
    days = listOf(
        DayOfWeek.MONDAY to this.days[0].second.value,
        DayOfWeek.TUESDAY to this.days[1].second.value,
        DayOfWeek.WEDNESDAY to this.days[2].second.value,
        DayOfWeek.THURSDAY to this.days[3].second.value,
        DayOfWeek.FRIDAY to this.days[4].second.value,
        DayOfWeek.SATURDAY to this.days[5].second.value,
        DayOfWeek.SUNDAY to this.days[6].second.value,
    )
)


