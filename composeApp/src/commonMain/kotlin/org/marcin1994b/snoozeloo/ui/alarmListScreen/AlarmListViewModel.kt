package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.marcin1994b.snoozeloo.Ticker
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult

class AlarmListViewModel(
    private val alarmDatabaseInteractor: AlarmDatabaseInteractor
) : ViewModel() {

    private val _alarmListData: MutableState<AlarmListViewState> = mutableStateOf(AlarmListViewState.Loading)
    val alarmListData: State<AlarmListViewState> = _alarmListData

    val currentLocalTime = mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))

    fun loadData() {
        viewModelScope.launch {
            when (val alarmDatabaseResult = alarmDatabaseInteractor.getAllAlarms()) {
                AlarmDatabaseResult.Failed -> {

                }

                is AlarmDatabaseResult.GetAllSuccess -> {
                    if (alarmDatabaseResult.list.isEmpty()) {
                        _alarmListData.value = AlarmListViewState.Empty
                    } else {
                        alarmDatabaseResult.list.sortedWith(
                            compareBy<Alarm> { it.alarmHour }.thenBy { it.alarmMinute }
                        ).let {
                            _alarmListData.value = AlarmListViewState.Loaded(it)
                        }

                        startTimeTicker()
                    }
                }

                else -> {
                    // do nothing
                }
            }
        }
    }

    fun switchAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarm.isOn = !alarm.isOn
            alarmDatabaseInteractor.updateAlarm(alarm)
        }
    }

    private fun CoroutineScope.startTimeTicker() {
        Ticker(
            tickInMillis = 1000,
            onTick = {
                val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                if (currentLocalTime.value.minute != time.minute) {
                    currentLocalTime.value = time
                }
            }
        )
    }

}

sealed class AlarmListViewState {
    data object Loading : AlarmListViewState()
    data object Empty : AlarmListViewState()
    data class Loaded(
        val alarms: List<Alarm>
    ) : AlarmListViewState()
}
