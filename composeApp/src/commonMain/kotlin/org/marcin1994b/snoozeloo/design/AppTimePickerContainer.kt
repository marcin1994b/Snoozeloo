package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.marcin1994b.snoozeloo.design.numberPicker.AMPMHours
import org.marcin1994b.snoozeloo.design.numberPicker.FullHours
import org.marcin1994b.snoozeloo.design.numberPicker.Hours
import org.marcin1994b.snoozeloo.design.numberPicker.HoursNumberPicker
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePickerContainer(timePickerState: TimePickerState) {

    var pickerValue by remember { mutableStateOf<Hours>(FullHours(9, 12)) }
    var textValue by remember { mutableStateOf("") }

    val currentTime = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
        .toLocalDateTime(TimeZone.currentSystemDefault())

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

                val alarmDateTime = LocalDateTime(
                    date = currentTime.date,
                    time = LocalTime(
                        hour = pickerValue.hours,
                        minute = pickerValue.minutes
                    )
                )

                val alarmDuration = alarmDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds().toDuration(DurationUnit.MILLISECONDS)
                val currentTimeDuration = currentTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds().toDuration(DurationUnit.MILLISECONDS)

                val tmp1 = alarmDuration.minus(currentTimeDuration)

                textValue = if (tmp1.isNegative()) {
                    "${23 + tmp1.inWholeHours}h ${(tmp1.inWholeMinutes % 60) + 60}m"
                } else {
                    "${tmp1.inWholeHours}h ${tmp1.inWholeMinutes % 60}m"
                }

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

        Spacer(modifier = Modifier.height(16.dp))

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
}