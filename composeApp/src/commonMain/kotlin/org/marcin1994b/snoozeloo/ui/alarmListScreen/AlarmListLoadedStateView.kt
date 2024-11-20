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
import org.marcin1994b.snoozeloo.db.AlarmEntity
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AlarmListLoadedStateView(items: List<AlarmEntity>) {
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
            item {
//                AlarmCardView(
//                    title = alarmData.name,
//                    timestamp = alarmData.time,
//                    isTurnOn = alarmData.isOn,
//                    repeatOn = alarmData.repeatOn,
//                    onSwitchClick = {}
//                )
            }

            item {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}