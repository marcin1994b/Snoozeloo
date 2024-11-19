package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.marcin1994b.snoozeloo.design.AlarmNameDialog
import org.marcin1994b.snoozeloo.design.AppRepeatOnRow
import org.marcin1994b.snoozeloo.design.AppRowWithSlider
import org.marcin1994b.snoozeloo.design.AppRowWithSwitch
import org.marcin1994b.snoozeloo.design.AppRowWithValue
import org.marcin1994b.snoozeloo.design.AppTimeInput
import org.marcin1994b.snoozeloo.design.AppTopBar
import org.marcin1994b.snoozeloo.getPlatform
import org.marcin1994b.snoozeloo.model.toMutableRepeatOn
import org.marcin1994b.snoozeloo.model.toRepeatOn
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

class SetAlarmNode(
    nodeContext: NodeContext,
    override val di: DI
) : LeafNode(nodeContext), DIAware {

    private val viewModel: SetAlarmViewModel by instance<SetAlarmViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(modifier: Modifier) {

        val alarmData = viewModel.alarmData.value
        val initialAlarmName = remember {
            mutableStateOf(alarmData.name)
        }

        val timePickerState = rememberTimePickerState(
            initialHour = alarmData.time.hour,
            initialMinute = alarmData.time.minute,
            true
        )

        val volumeSliderState = SliderState(
            value = alarmData.volume,
            steps = 100,
        )

        val repeatOnState = remember {
            mutableStateOf(alarmData.repeatOn.toMutableRepeatOn())
        }

        val vibrateState = remember {
            mutableStateOf(alarmData.shouldVibrate)
        }

        val isAlarmNameDialogDisplayed = remember {
            mutableStateOf(false)
        }

        if (isAlarmNameDialogDisplayed.value) {
            AlarmNameDialog(
                alarmName = initialAlarmName.value,
                onSaveClick = { alarmName ->
                    isAlarmNameDialogDisplayed.value = false
                    initialAlarmName.value = alarmName
                },
                onDismiss = {
                    isAlarmNameDialogDisplayed.value = false
                }
            )
        }

        Column(
            modifier = Modifier.background(color = AppColors.Grey075).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar(
                title = "Add New Alarm",
                onBackButtonPress = {}
            )

            Column(
                modifier = Modifier.background(color = AppColors.Grey075).fillMaxSize()
                    .padding(16.dp)
            ) {

                TimeContainer(
                    timePickerState = timePickerState
                )

                Spacer(Modifier.height(16.dp))

                AppRowWithValue(
                    label = "Alarm Name",
                    value = initialAlarmName.value.toString(),
                    onClick = {
                        isAlarmNameDialogDisplayed.value = true
                    }
                )

                Spacer(Modifier.height(16.dp))

                AppRepeatOnRow(
                    repeatOn = repeatOnState.value
                )

                Spacer(Modifier.height(16.dp))

                if (getPlatform().name != "iOS") {
                    AppRowWithValue(
                        label = "Alarm Ringtone",
                        value = "Default",
                        onClick = {}
                    )

                    Spacer(Modifier.height(16.dp))

                    AppRowWithSlider(
                        label = "Alarm Volume",
                        sliderState = volumeSliderState
                    )

                    Spacer(Modifier.height(16.dp))
                }

                AppRowWithSwitch(
                    label = "Vibrate",
                    isSwitched = vibrateState.value,
                    onSwitch = { vibrateState.value = it }
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.saveAlarm(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute,
                                alarmName = initialAlarmName.value ?: "",
                                vibrate = vibrateState.value,
                                volume = volumeSliderState.value,
                                repeatOn = repeatOnState.value.toRepeatOn(),
                                ringtone = ""
                            )
                        },
                        modifier = Modifier.width(240.dp).height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.BrandBlue,
                            contentColor = AppColors.White,
                            disabledContainerColor = AppColors.BrandBlue,
                            disabledContentColor = AppColors.White
                        )
                    ) {
                        Text(
                            text = "Save",
                            style = AppTheme.typography.headline6 + AppTheme.typography.bold,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContainer(timePickerState: TimePickerState) {

    Column(
        modifier = Modifier.fillMaxWidth().background(
            color = AppColors.White,
            shape = AppTheme.shapes.medium
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Box(
            modifier = Modifier.height(100.dp)
        ) {
            AppTimeInput(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    .focusable(enabled = true, interactionSource = null),
                timePickerState = timePickerState
            )
        }
    }
}