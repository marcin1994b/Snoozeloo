package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.model.getAlarmInText
import org.marcin1994b.snoozeloo.model.toText
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AlarmCardView(
    alarm: Alarm,
    currentTime: LocalDateTime,
    onSwitchClick: (Alarm) -> Unit,
    onItemClick: (Alarm) -> Unit
) {

    val hourToDisplay = if (alarm.alarmHour < 10) "0${alarm.alarmHour}" else alarm.alarmHour
    val minuteToDisplay = if (alarm.alarmMinute < 10) "0${alarm.alarmMinute}" else alarm.alarmMinute

    var switchValue by remember {
        mutableStateOf(alarm.isOn)
    }

    var alarmInText by remember {
        mutableStateOf(
            "Alarm in " + getAlarmInText(alarm.alarmHour, alarm.alarmMinute, alarm.repeatOn)
        )
    }

    val textColor = if (switchValue) {
        AppColors.Black
    } else {
        AppColors.Grey400
    }

    LaunchedEffect(key1 = currentTime) {
        alarmInText = "Alarm in " + getAlarmInText(alarm.alarmHour, alarm.alarmMinute, alarm.repeatOn)
    }

    Box(
        modifier = Modifier.background(
            color = AppColors.White,
            shape = AppTheme.shapes.medium
        )
            .fillMaxWidth()
            .clickable { onItemClick(alarm) }
            .padding(vertical = 8.dp)
    ) {
        Column(
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    text = alarm.name ?: "Default",
                    style = AppTheme.typography.headline5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )

                AppSwitch(
                    isChecked = switchValue,
                    onCheckChanged = {
                        switchValue = !switchValue
                        onSwitchClick(alarm)
                    }
                )
            }

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "$hourToDisplay:$minuteToDisplay",
                style = AppTheme.typography.headline3,
                color = textColor
            )

            if (switchValue) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = alarmInText,
                    style = AppTheme.typography.subtitle1,
                    color = AppColors.Grey500
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (alarm.repeatOn.days.any { it.second }) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    alarm.repeatOn.days.forEach {
                        AppChips(
                            label = it.first.toText(),
                            isSelected = it.second,
                            isEnabled = switchValue
                        )
                    }
                }
            }
        }
    }
}