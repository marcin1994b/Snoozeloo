package org.marcin1994b.snoozeloo.ui.alarmTriggerScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme
import snoozeloo.composeapp.generated.resources.Res
import snoozeloo.composeapp.generated.resources.alarm_icon

class AlarmTriggerNode(
    nodeContext: NodeContext,
    private val alarmId: String,
    override val di: DI
) : LeafNode(nodeContext), DIAware {

    private val viewModel: AlarmTriggeredViewModel by instance()

    @Composable
    override fun Content(modifier: Modifier) {

        LaunchedEffect(Unit) {
            viewModel.initAlarmData(alarmId.toLong())
        }

        LaunchedEffect(key1 = viewModel.finishView.value) {
            if (viewModel.finishView.value) {
                finish()
            }
        }

        viewModel.alarmData.value?.let { alarm ->
            AlarmTriggeredScreen(
                alarm = alarm,
                onCancelClick = {
                    viewModel.onAlarmCancel()
                },
                onSnoozeClick = {
                    viewModel.onAlarmSnooze()
                }
            )
        }
    }
}

@Composable
fun AlarmTriggeredScreen(
    alarm: Alarm,
    onCancelClick: () -> Unit,
    onSnoozeClick: () -> Unit
) {

    val hourToDisplay = if (alarm.alarmHour < 10) "0${alarm.alarmHour}" else alarm.alarmHour
    val minuteToDisplay = if (alarm.alarmMinute < 10) "0${alarm.alarmMinute}" else alarm.alarmMinute

    Box(
        modifier = Modifier.fillMaxSize().background(color = AppColors.Grey075),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.alarm_icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppColors.BrandBlue)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "${hourToDisplay}:${minuteToDisplay}",
                style = AppTheme.typography.headline1 + AppTheme.typography.bold,
                color = AppColors.BrandBlue
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = alarm.name?.capitalize() ?: "DEFAULT",
                style = AppTheme.typography.headline3 + AppTheme.typography.bold,
                color = AppColors.Black
            )

            Spacer(Modifier.height(26.dp))

            Button(
                modifier = Modifier.width(240.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.BrandBlue,
                    contentColor = AppColors.White
                ),
                onClick = onCancelClick,
            ) {
                Text(
                    text = "Turn Off",
                    style = AppTheme.typography.headline6 + AppTheme.typography.bold,
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.width(240.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.BrandBlue.copy(alpha = 0.2f),
                    contentColor = AppColors.BrandBlue
                ),
                onClick = onSnoozeClick,
            ) {
                Text(
                    text = "Snooze for 10 min",
                    style = AppTheme.typography.headline6 + AppTheme.typography.bold,
                )
            }
        }
    }
}