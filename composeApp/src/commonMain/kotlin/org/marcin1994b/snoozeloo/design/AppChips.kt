package org.marcin1994b.snoozeloo.design

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.marcin1994b.snoozeloo.theme.AppColors

@Composable
fun AppChips(
    label: String,
    isSelected: Boolean,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    if (isSelected) {
        SelectedChips(label, isEnabled, onClick)
    } else {
        UnselectedChips(label, isEnabled, onClick)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectedChips(
    label: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isEnabled) AppColors.BrandBlue else AppColors.BrandBlue.copy(alpha = 0.3f),
            contentColor = if (isEnabled) AppColors.White else AppColors.White
        )
    ) {
        Text(label)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UnselectedChips(
    label: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isEnabled) AppColors.Grey200 else AppColors.Grey200.copy(alpha = 0.3f),
            contentColor = if (isEnabled) AppColors.Black else AppColors.Black.copy(alpha = 0.3f),
        )
    ) {
        Text(label)
    }
}