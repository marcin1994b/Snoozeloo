package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRowWithSlider(
    label: String,
    sliderState: SliderState,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = AppColors.White,
                shape = AppTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = AppTheme.typography.headline6 + AppTheme.typography.bold,
        )

        Slider(
            state = sliderState,
            colors = SliderDefaults.colors(
                thumbColor = AppColors.White,
                activeTrackColor = AppColors.BrandBlue,
                activeTickColor = AppColors.BrandBlue,
                inactiveTrackColor = AppColors.BrandBlue.copy(alpha = 0.1f),
                inactiveTickColor = AppColors.BrandBlue.copy(alpha = 0.1f)
            )
        )
    }
}