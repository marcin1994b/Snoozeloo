package org.marcin1994b.snoozeloo.design

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.marcin1994b.snoozeloo.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimeInput(
    modifier: Modifier,
    timePickerState: TimePickerState
) {
    TimeInput(
        state = timePickerState,
        modifier = modifier,
        colors = TimePickerDefaults.colors(
            containerColor = AppColors.Grey075,
            timeSelectorSelectedContainerColor = AppColors.Grey075,
            timeSelectorUnselectedContainerColor = AppColors.Grey075,
            timeSelectorSelectedContentColor = AppColors.BrandBlue,
            timeSelectorUnselectedContentColor = AppColors.BrandBlue,
            periodSelectorBorderColor = AppColors.White,
            periodSelectorSelectedContainerColor = AppColors.BrandBlue.copy(alpha = 0.2f),
            periodSelectorUnselectedContainerColor = AppColors.Grey075,
            periodSelectorSelectedContentColor = AppColors.BrandBlue,
            periodSelectorUnselectedContentColor = AppColors.BrandBlue,
        )
    )
}