package org.marcin1994b.snoozeloo.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.marcin1994b.snoozeloo.theme.AppColors
import org.marcin1994b.snoozeloo.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmNameDialog(
    alarmName: String?,
    onSaveClick: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val inputValue = remember {
        mutableStateOf(alarmName ?: "Default")
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.background(
            color = AppColors.White,
            shape = AppTheme.shapes.medium
        ).padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Enter your alarm name",
                style = AppTheme.typography.headline6
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = inputValue.value,
                onValueChange = { text -> inputValue.value = text },
                singleLine = true
            )

            Spacer(Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    modifier = Modifier.width(120.dp),
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.White,
                        contentColor = AppColors.Black,
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = AppTheme.typography.button1
                    )
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                        onSaveClick.invoke(inputValue.value)
                    }
                ) {
                    Text(
                        text = "Save",
                        style = AppTheme.typography.button1
                    )
                }
            }
        }
    }
}