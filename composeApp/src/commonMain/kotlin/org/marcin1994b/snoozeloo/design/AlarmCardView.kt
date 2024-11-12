package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.model.DayOfWeek
import org.marcin1994b.snoozeloo.model.formatToAlarmTime
import org.marcin1994b.snoozeloo.model.getAlarmCountDownText
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlarmCardView(
    title: String?,
    timestamp: Long,
    isTurnOn: Boolean,
    repeatOn: List<Pair<DayOfWeek, Boolean>>,
    onSwitchClick: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.background(
            color = AppColors.White,
            shape = AppTheme.shapes.medium
        ).fillMaxWidth().height(180.dp).padding(vertical = 16.dp)
    ) {
        Box(modifier = Modifier.padding(start = 16.dp).fillMaxWidth().height(105.dp)) {
            Text(
                modifier = Modifier.align(Alignment.TopStart).padding(bottom = 30.dp),
                text = title ?: "Unknown",
                style = AppTheme.typography.headline6 + AppTheme.typography.bold,
            )

            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = timestamp.formatToAlarmTime(),
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
                text = timestamp.getAlarmCountDownText(),
                style = AppTheme.typography.subtitle1,
                color = AppColors.Grey500
            )

            Switch(
                modifier = Modifier.align(Alignment.TopEnd).width(100.dp).height(50.dp),
                checked = isTurnOn,
                onCheckedChange = { onSwitchClick(it) },
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            repeatOn.forEach {
                Chip(
                    onClick = {}
                ) {
                    Text(it.first.toString())
                }
            }
        }
    }
}