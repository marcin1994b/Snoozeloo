package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.marcin1994b.snoozeloo.model.Alarm
import org.marcin1994b.snoozeloo.model.GetAlarms

class AlarmListViewModel(
    private val getAlarms: GetAlarms
) : ViewModel() {

    private val _alarmListData: MutableState<AlarmListViewState> = mutableStateOf(AlarmListViewState.Loading)
    val alarmListData: State<AlarmListViewState> = _alarmListData

    init {
        _alarmListData.value = if (getAlarms.mock.isNotEmpty()) {
            AlarmListViewState.Loaded(getAlarms.mock)
        } else {
            AlarmListViewState.Empty
        }
    }

}

sealed class AlarmListViewState {
    data object Loading : AlarmListViewState()
    data object Empty : AlarmListViewState()
    data class Loaded(
        val alarms: List<Alarm>
    ) : AlarmListViewState()
}
