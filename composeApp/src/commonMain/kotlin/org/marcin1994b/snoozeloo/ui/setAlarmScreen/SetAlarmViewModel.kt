package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.marcin1994b.snoozeloo.feedback.AppFeedback
import org.marcin1994b.snoozeloo.feedback.AppFeedbackMsg
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseInteractor
import org.marcin1994b.snoozeloo.interactors.AlarmDatabaseResult

class SetAlarmViewModel(
    private val alarmDatabaseInteractor: AlarmDatabaseInteractor
) : ViewModel() {

    val finishView = mutableStateOf(false)
    val alarmData = mutableStateOf<Alarm?>(null)

    fun initViewData(alarmId: Int?) {
        viewModelScope.launch {
             if (alarmId != null) {
                alarmDatabaseInteractor.getAlarmById(alarmId).let { result ->
                    if (result is AlarmDatabaseResult.GetAllSuccess) {
                        alarmData.value = result.list.first()
                    } else {
                        alarmData.value = Alarm.mock
                    }
                }
            } else {
                 alarmData.value = Alarm.mock
            }
        }
    }

    fun saveAlarm(alarm: Alarm) {
        viewModelScope.launch {
            val result = alarmDatabaseInteractor.addAlarm(alarm)

            if (result is AlarmDatabaseResult.Success) {
                finishView.value = true

                AppFeedback.data.emit(if (alarm.id == 0) {
                    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmAddedSuccessfully
                } else {
                    AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmUpdatedSuccessfully
                })
            } else {
                AppFeedback.data.emit(AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmAddingFailed)
            }
        }
    }

    fun deleteAlarm() {
        viewModelScope.launch {
            alarmData.value?.let { alarm ->
                val result = alarmDatabaseInteractor.deleteAlarm(alarm)

                if (result is AlarmDatabaseResult.Success) {
                    finishView.value = true
                    AppFeedback.data.emit(AppFeedbackMsg.AppFeedbackPositiveMsg.AlarmDeletedSuccessfully)
                } else {
                    AppFeedback.data.emit(AppFeedbackMsg.AppFeedbackNegativeMsg.AlarmDeletingFailed)
                }
            }
        }
    }

}