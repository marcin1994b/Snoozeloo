package org.marcin1994b.snoozeloo.ui.alarmTriggerScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.marcin1994b.snoozeloo.alarmScheduler.AlarmScheduler
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult

class AlarmTriggeredViewModel(
    private val alarmDatabaseInteractor: AlarmDatabaseInteractor,
    private val alarmScheduler: AlarmScheduler
): ViewModel() {

    val alarmData = mutableStateOf<Alarm?>(null)
    val finishView = mutableStateOf(false)

    fun initAlarmData(alarmId: Long) {
        viewModelScope.launch {
            val result = alarmDatabaseInteractor.getAlarmById(alarmId)

            if (result is AlarmDatabaseResult.GetAllSuccess) {
                alarmData.value = result.list.first()
            } else {
                alarmData.value = null
            }
        }
    }

    fun onAlarmCancel() {
        alarmData.value?.let {
            alarmScheduler.cancel(it)
            finishView.value = true
        }
    }

    fun onAlarmSnooze() {
        alarmData.value?.let {
            alarmScheduler.snooze(it)
            finishView.value = true
        }
    }
}