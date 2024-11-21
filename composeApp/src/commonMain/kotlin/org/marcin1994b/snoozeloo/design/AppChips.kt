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
    onClick: () -> Unit = {}
) {
    if (isSelected) {
        SelectedChips(label, onClick)
    } else {
        UnselectedChips(label, onClick)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectedChips(
    label: String,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = AppColors.BrandBlue,
            contentColor = AppColors.White
        )
    ) {
        Text(label)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UnselectedChips(
    label: String,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = AppColors.Grey200,
            contentColor = AppColors.Black
        )
    ) {
        Text(label)
    }
}