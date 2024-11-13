package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.theme.AppColors

@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier.padding(bottom = 20.dp, end = 20.dp),
        onClick = onClick,
        backgroundColor = AppColors.BrandBlue,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null
        )
    }
}
