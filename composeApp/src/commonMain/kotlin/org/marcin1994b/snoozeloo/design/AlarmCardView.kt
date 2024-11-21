package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.model.toText
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AlarmCardView(
    alarm: Alarm,
    onSwitchClick: (Boolean) -> Unit,
    onItemClick: (Alarm) -> Unit
) {
    Column(
        modifier = Modifier.background(
            color = AppColors.White,
            shape = AppTheme.shapes.medium
        ).fillMaxWidth().padding(vertical = 16.dp).clickable { onItemClick(alarm) }
    ) {
        Box(modifier = Modifier.padding(start = 16.dp).fillMaxWidth().height(105.dp)) {
            Text(
                modifier = Modifier.align(Alignment.TopStart).padding(bottom = 30.dp),
                text = alarm.name ?: "Default",
                style = AppTheme.typography.headline6 + AppTheme.typography.bold,
            )

            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${alarm.time.hour}:${alarm.time.minute}",
                    style = AppTheme.typography.headline3,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "AM",
                    style = AppTheme.typography.headline4,
                )
            }

            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = "Alarm in 2h 30m",//timestamp.getAlarmCountDownText(),
                style = AppTheme.typography.subtitle1,
                color = AppColors.Grey500
            )

            AppSwitch(
                modifier = Modifier.align(Alignment.TopEnd).width(100.dp).height(50.dp),
                isChecked = alarm.isOn,
                onCheckChanged = { onSwitchClick(it) },
            )
        }

        if (alarm.repeatOn.days.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                alarm.repeatOn.days.forEach {
                    AppChips(
                        label = it.first.toText(),
                        isSelected = it.second
                    )
                }
            }
        }
    }
}