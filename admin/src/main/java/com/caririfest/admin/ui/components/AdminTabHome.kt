package com.caririfest.admin.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caririfest.admin.ui.screens.appscreens.producer_area.AdminEventContentScreen
import com.caririfest.admin.ui.screens.appscreens.producer_metric.AdminMetricContentScreen

@Composable
fun AdminTabHome() {

    val tabs = listOf(
        AdminTab("MEUS EVENTOS"),
        AdminTab("MÉTRICAS")
    )
    // Aba selecionada
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedIndex,
            modifier = Modifier
                .width(300.dp)
                .padding(end = 56.dp),
        ) {
            tabs.forEachIndexed { index, tab ->

                Tab(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    text = { Text(tab.title, fontSize = 12.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                Color.White
            ),
            elevation = CardDefaults.cardElevation(
                16.dp
            )
        ) {
            when (selectedIndex) {
                0 -> AdminEventContentScreen()
                1 -> AdminMetricContentScreen()
            }
        }
    }
}


data class AdminTab(
    val title: String
)