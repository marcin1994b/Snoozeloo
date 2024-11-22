package org.marcin1994b.snoozeloo.ui.setAlarmScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.marcin1994b.snoozeloo.db.Alarm
import org.marcin1994b.snoozeloo.design.AlarmNameDialog
import org.marcin1994b.snoozeloo.design.AppRepeatOnRow
import org.marcin1994b.snoozeloo.design.AppRowWithSlider
import org.marcin1994b.snoozeloo.design.AppRowWithSwitch
import org.marcin1994b.snoozeloo.design.AppRowWithValue
import org.marcin1994b.snoozeloo.design.numberPicker.FullHours
import org.marcin1994b.snoozeloo.design.numberPicker.Hours
import org.marcin1994b.snoozeloo.design.numberPicker.HoursNumberPicker
import org.marcin1994b.snoozeloo.getPlatform
import org.marcin1994b.snoozeloo.model.getAlarmInText
import org.marcin1994b.snoozeloo.model.toMutableRepeatOn
import org.marcin1994b.snoozeloo.model.toRepeatOn
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetAlarmScreen(
    alarmData: Alarm,
    currentTime: LocalDateTime,
    onSaveButtonClick: (Alarm) -> Unit,
) {

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

    var pickerValue by remember { mutableStateOf<Hours>(FullHours(9, 12)) }
    var textValue by remember { mutableStateOf("") }

    LaunchedEffect(key1 = pickerValue.hours, key2 = pickerValue.minutes, key3 = currentTime) {
        textValue = getAlarmInText(pickerValue.hours, pickerValue.minutes, repeatOnState.value.toRepeatOn())
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().background(
                color = AppColors.White,
                shape = AppTheme.shapes.medium
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            HoursNumberPicker(
                dividersColor = AppColors.BrandBlue,
                value = pickerValue,
                onValueChange = {
                    pickerValue = it
                },
                hoursDivider = {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center,
                        text = ":"
                    )
                },
                minutesDivider = {
                    Spacer(Modifier.width(8.dp))
                },
                textStyle = AppTheme.typography.headline6
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (textValue.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center,
                    color = AppColors.Grey400,
                    text = "Alarm in ${textValue}",
                    style = AppTheme.typography.headline6
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

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
            repeatOn = repeatOnState.value,
            onAnyChipsClick = {
                textValue = getAlarmInText(pickerValue.hours, pickerValue.minutes, repeatOnState.value.toRepeatOn())
            }
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
                modifier = Modifier.width(240.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.BrandBlue,
                    contentColor = AppColors.White,
                    disabledContainerColor = AppColors.BrandBlue,
                    disabledContentColor = AppColors.White
                ),
                onClick = {
                    val newTime = LocalDateTime(
                        date = alarmData.time.date, // probably should add 1 day if past midnight
                        time = LocalTime(timePickerState.hour, timePickerState.minute, 0, 0)
                    )
                    Alarm(
                        id = alarmData.id,
                        name = initialAlarmName.value,
                        time = newTime,
                        isOn = true,
                        ringtoneName = "TODO",
                        volume = volumeSliderState.value,
                        shouldVibrate = vibrateState.value,
                        repeatOn = repeatOnState.value.toRepeatOn()
                    ).let { onSaveButtonClick(it) }
                },
            ) {
                Text(
                    text = "Save",
                    style = AppTheme.typography.headline6 + AppTheme.typography.bold,
                )
            }
        }
    }
}