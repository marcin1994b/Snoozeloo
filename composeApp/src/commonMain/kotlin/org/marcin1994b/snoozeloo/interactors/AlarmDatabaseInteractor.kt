package org.marcin1994b.snoozeloo.interactors

import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.db.AppDatabase

class AlarmDatabaseInteractor(
    private val database: AppDatabase
) {

    suspend fun addAlarm(alarm: Alarm): AlarmDatabaseResult {
        return try {
            database.alarmDao().insert(alarm)
            AlarmDatabaseResult.Success
        } catch (e: Exception) {
            AlarmDatabaseResult.Failed
        }
    }

    suspend fun updateAlarm(alarm: Alarm): AlarmDatabaseResult {
        return try {
            database.alarmDao().insert(alarm)
            AlarmDatabaseResult.Success
        } catch (e: Exception) {
            AlarmDatabaseResult.Failed
        }
    }

    suspend fun deleteAlarm(alarm: Alarm): AlarmDatabaseResult {
        return try {
            database.alarmDao().delete(alarm)
            AlarmDatabaseResult.Success
        } catch (e: Exception) {
            AlarmDatabaseResult.Failed
        }
    }

    suspend fun getAllAlarms(): AlarmDatabaseResult {
        return try {
            val alarms = database.alarmDao().getAllAlarms()
            AlarmDatabaseResult.GetAllSuccess(alarms)
        } catch (e: Exception) {
            AlarmDatabaseResult.Failed
        }
    }

    suspend fun getAlarmById(alarmId: Int): AlarmDatabaseResult {
        return try {
            database.alarmDao().getAlarmById(alarmId)?.let {
                AlarmDatabaseResult.GetAllSuccess(listOf(it))
            } ?: AlarmDatabaseResult.Failed
        } catch (e: Exception) {
            AlarmDatabaseResult.Failed
        }
    }
}

sealed interface AlarmDatabaseResult {
    data object Failed: AlarmDatabaseResult
    data object Success: AlarmDatabaseResult
    data class GetAllSuccess(val list: List<Alarm>): AlarmDatabaseResult
}