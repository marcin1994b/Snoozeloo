package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult

class AlarmListViewModel(
    private val alarmDatabaseInteractor: AlarmDatabaseInteractor
) : ViewModel() {

    private val _alarmListData: MutableState<AlarmListViewState> = mutableStateOf(AlarmListViewState.Loading)
    val alarmListData: State<AlarmListViewState> = _alarmListData

    fun loadData() {
        viewModelScope.launch {
            when (val alarmDatabaseResult = alarmDatabaseInteractor.getAllAlarms()) {
                AlarmDatabaseResult.Failed -> {

                }

                is AlarmDatabaseResult.GetAllSuccess -> {
                    _alarmListData.value = if (alarmDatabaseResult.list.isNotEmpty()) {
                        AlarmListViewState.Loaded(alarmDatabaseResult.list)
                    } else {
                        AlarmListViewState.Empty
                    }
                }

                else -> {

                }
            }
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
