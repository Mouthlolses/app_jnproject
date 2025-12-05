package com.caririfest.admin.ui.screens.appscreens.producer_metric

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.caririfest.admin.ui.components.InfoRow

@Composable
fun AdminMetricContentScreen() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        InfoRow(
            label = "Publique um evento pela primeira vez!",
            value = "Aqui você pode gerenciar e observar todas as métricas relacionadas ao seu evento, vamos lá!",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Precisa de ajuda para criar?", color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))

    }
}
