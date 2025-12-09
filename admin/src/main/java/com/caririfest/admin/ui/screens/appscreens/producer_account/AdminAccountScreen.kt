package com.caririfest.admin.ui.screens.appscreens.producer_account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.caririfest.admin.R
import com.caririfest.admin.ui.screens.validation_screens.producer_auth_account.ProducerAuthViewModel

@Composable
fun AdminAccountScreen() {

    val producerAuthViewModel: ProducerAuthViewModel = hiltViewModel()

    val uiState by producerAuthViewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            16.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = uiState.adminName
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = uiState.adminEmail
            )
            Spacer(modifier = Modifier.padding(vertical = 18.dp))
        }
    }
}