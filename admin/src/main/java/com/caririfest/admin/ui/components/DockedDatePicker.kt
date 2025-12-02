package com.caririfest.admin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DockedDatePicker(
    label: @Composable (() -> Unit)? = null
) {

    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    var selectedDateText by remember { mutableStateOf(formatDate(datePickerState.selectedDateMillis)) }

    OutlinedTextField(
        value = selectedDateText,
        onValueChange = { }, // Não permite edição direta
        label = label,
        readOnly = true,
        // Ao clicar no campo, mostra o diálogo
        trailingIcon = {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.CalendarToday,
                contentDescription = "Selecionar Data",
                modifier = androidx.compose.ui.Modifier.clickable { showDialog = true }
            )
        }
    )

    // 4. O Diálogo (DatePickerDialog)
    if (showDialog) {
        DatePickerDialog(
            // Chamado quando o botão "Cancel" é pressionado
            onDismissRequest = {
                showDialog = false
            },
            // Botão de Confirmação
            confirmButton = {
                TextButton(
                    onClick = {
                        // Atualiza a data selecionada no campo de texto
                        selectedDateText = formatDate(datePickerState.selectedDateMillis)
                        showDialog = false // Fecha o diálogo
                    }
                ) {
                    Text("OK")
                }
            },
            // Botão de Cancelamento
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false // Fecha o diálogo
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            // O conteúdo real do Diálogo é o componente DatePicker
            DatePicker(
                state = datePickerState
            )
        }
    }

}


// Função auxiliar para formatar os milissegundos para o formato desejado (MM/dd/yyyy)
fun formatDate(millis: Long?): String {
    if (millis == null) return ""
    // O DatePicker M3 usa UTC, então a conversão é crucial
    val date = Date(millis)
    val format = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    // O seu exemplo mostra um formato americano (MM/DD/YYYY)
    return format.format(date)
}