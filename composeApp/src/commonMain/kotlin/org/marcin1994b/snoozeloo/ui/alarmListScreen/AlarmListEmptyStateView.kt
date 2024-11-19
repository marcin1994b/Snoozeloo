package org.marcin1994b.snoozeloo.ui.alarmListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.alarm_icon

@Composable
fun AlarmListEmptyStateView() {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.TopStart),
            text = "Your Alarms",
            style = AppTheme.typography.headline5
        )

        Box(
            modifier = Modifier.fillMaxWidth().height(164.dp).align(Alignment.Center),
        ) {
            Image(
                modifier = Modifier.align(Alignment.TopCenter),
                painter = painterResource(Res.drawable.alarm_icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = AppColors.BrandBlue
                )
            )

            Text(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                text = "It's empty! Add the first alarm so you don't miss an important moment!",
                style = AppTheme.typography.headline6,
                textAlign = TextAlign.Center
            )
        }
    }
}