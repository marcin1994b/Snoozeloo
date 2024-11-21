package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePickerContainer(timePickerState: TimePickerState) {
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