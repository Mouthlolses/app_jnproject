package com.caririfest.app.payment.stripe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stripe.android.paymentsheet.PaymentSheet


//Necessário criar validação de campos.
//Permitir usuário concluir pagamento apenas com os campos completos.

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel,
    paymentSheet: PaymentSheet,
    onPayClicked: (PaymentSheet, String) -> Unit
) {
    val clientSecret by viewModel.clientSecret.collectAsState()
    val error by viewModel.error.collectAsState()

    var buyerName by remember { mutableStateOf("") }
    var buyerSurname by remember { mutableStateOf("") }
    var buyerEmail by remember { mutableStateOf("") }
    var buyerEmailConfirm by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        viewModel.fetchPaymentIntent()
    }

    error?.let { message ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Erro") },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { viewModel.clearError() }) {
                    Text("Ok")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 26.dp),
            colors = CardDefaults.cardColors(
                Color.White
            ),
            elevation = CardDefaults.cardElevation(
                18.dp
            )
        ) {
            Column {

                Spacer(modifier = Modifier.padding(top = 28.dp))

                OutlinedTextField(
                    value = buyerName,
                    onValueChange = { buyerName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp),
                    label = { Text("Nome") }
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                OutlinedTextField(
                    value = buyerSurname,
                    onValueChange = { buyerSurname = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp),
                    label = { Text("Sobrenome") }
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                OutlinedTextField(
                    value = buyerEmail,
                    onValueChange = { buyerEmail = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp),
                    label = { Text("E-mail") }
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                OutlinedTextField(
                    value = buyerEmailConfirm,
                    onValueChange = { buyerEmailConfirm = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 26.dp),
                    label = { Text("Confirmação de E-mail") }
                )


                Spacer(modifier = Modifier.padding(bottom = 28.dp))
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            enabled = clientSecret != null,
            onClick = { clientSecret?.let { onPayClicked(paymentSheet, it) } }
        ) {
            Text("Pay Now")
        }
    }
}
