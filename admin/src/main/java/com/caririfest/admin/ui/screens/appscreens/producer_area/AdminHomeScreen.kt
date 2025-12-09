package com.caririfest.admin.ui.screens.appscreens.producer_area

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.caririfest.admin.ui.components.AdminTabHome
import com.caririfest.admin.ui.components.InfoRowWithButton
import com.caririfest.admin.ui.screens.validation_screens.producer_auth_account.ProducerAuthViewModel

// ÁREA DO PRODUTOR -  Boa tarde Mattheus! Já publicou seu evento?

// Opção - Criar Evento.

// Card com Eventos Criados - Meus eventos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(myNavController: NavController) {

    val producerAuthViewModel: ProducerAuthViewModel = hiltViewModel()

    val uiState by producerAuthViewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                Color.White
            ),
            elevation = CardDefaults.cardElevation(
                16.dp
            )
        ) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoRowWithButton(
                    label = "Boa Tarde ${uiState.adminName}!",
                    value = "Já Publicou seu evento?",
                    onClick = { myNavController.navigate("createEventScreen") }
                )
            }
            Spacer(modifier = Modifier.padding(top = 22.dp))
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdminTabHome()
    }
}
