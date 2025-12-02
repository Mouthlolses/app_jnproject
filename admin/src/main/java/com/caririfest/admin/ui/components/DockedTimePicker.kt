package com.caririfest.admin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedTimePicker(
    label: @Composable (() -> Unit)? = null,
) {

    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()

    var selectedTimeText by remember { mutableStateOf(formatTime(timePickerState)) }

    OutlinedTextField(
        value = selectedTimeText,
        onValueChange = { },
        readOnly = true,
        label = label,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Selecionar Hora",
                modifier = Modifier.clickable(
                    onClick = { showDialog = true }
                )
            )
        }
    )

    if (showDialog) {
        TimePickerDialog(
            title = { },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTimeText = formatTime(timePickerState)
                        showDialog = false
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }) {
            TimePicker(
                state = timePickerState,
            )
        }
    }
}


fun formatTime(state: TimePickerState): String {
    return LocalTime.of(state.hour, state.minute)
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}