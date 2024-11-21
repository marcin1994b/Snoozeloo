package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AppRowWithSwitch(
    label: String,
    isSwitched: Boolean,
    onSwitch: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = AppColors.White,
                shape = AppTheme.shapes.medium
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = AppTheme.typography.headline6 + AppTheme.typography.bold,
        )

        AppSwitch(
            modifier = Modifier.height(25.dp),
            isChecked = isSwitched,
            onCheckChanged = onSwitch
        )
    }
}