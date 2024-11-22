package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.design.AlarmCardView
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AlarmListLoadedStateView(
    items: List<Alarm>,
    currentTime: LocalDateTime,
    onAlarmItemClick: (Alarm) -> Unit,
    onAlarmSwitchClick: (Alarm) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Your Alarms",
                style = AppTheme.typography.headline5
            )
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        items.forEach { alarmData ->
            item(key = alarmData.id) {
                AlarmCardView(
                    alarm = alarmData,
                    currentTime = currentTime,
                    onSwitchClick = onAlarmSwitchClick,
                    onItemClick = onAlarmItemClick
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}