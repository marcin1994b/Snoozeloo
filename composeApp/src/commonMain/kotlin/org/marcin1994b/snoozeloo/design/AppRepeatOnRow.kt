package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.model.MutableRepeatOn
import org.marcin1994b.snoozeloo.model.toText
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@Composable
fun AppRepeatOnRow(
    repeatOn: MutableRepeatOn,
    onAnyChipsClick: () -> Unit
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
            text = "Repeat On",
            style = AppTheme.typography.headline6 + AppTheme.typography.bold,
        )

        Spacer(Modifier.height(4.dp))

        Row {
            repeatOn.days.forEachIndexed { index, day ->
                AppChips(
                    label = day.first.toText(),
                    isSelected = day.second.value,
                    onClick = {
                        repeatOn.days[index].second.value = !day.second.value
                        onAnyChipsClick()
                    }
                )
            }
        }
    }
}



