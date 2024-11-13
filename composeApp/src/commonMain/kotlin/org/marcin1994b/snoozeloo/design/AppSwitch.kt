package org.marcin1994b.snoozeloo.design


import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.marcin1994b.snoozeloo.theme.AppColors

@Composable
fun AppSwitch(
    modifier: Modifier,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Switch(
        modifier = modifier,
        checked = isChecked,
        onCheckedChange = onCheckChanged,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppColors.White,
            checkedTrackColor = AppColors.BrandBlue,
            checkedBorderColor = AppColors.White,
            checkedIconColor = AppColors.White,
            uncheckedThumbColor = AppColors.White,
            uncheckedBorderColor = AppColors.White,
            uncheckedIconColor = AppColors.White,
        )
    )
}