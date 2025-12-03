package com.caririfest.admin.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caririfest.admin.R
import com.caririfest.admin.ui.components.AdminDrawer
import com.caririfest.admin.ui.screens.producer_account.AdminAccountScreen
import com.caririfest.admin.ui.screens.producer_area.AdminHomeScreen
import com.caririfest.admin.ui.screens.producer_creation.CreateEventScreen
import com.caririfest.admin.ui.screens.producer_metric.AdminMetricScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf("") }

    AdminDrawer(
        drawerState = drawerState,
        selectedItem = selected,
        onItemClick = { route ->
            selected = route
            navController.navigate(route) {
                launchSingleTop = true
            }
            scope.launch { drawerState.close() }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("CaririFestAdmin")
                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_action),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable(
                                    onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                )
                        )
                    },
                    actions = {
                        Icon(
                            painter = painterResource(R.drawable.ic_account_circle_action),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate("adminAccountScreen")
                                    }
                                )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        Color.Gray,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = "adminScreen",
                modifier = Modifier.padding(
                    innerPadding
                )
            ) {
                composable("adminScreen") { AdminHomeScreen(navController) }
                composable("metricScreen") { AdminMetricScreen() }
                composable("createEventScreen") { CreateEventScreen() }
                composable("adminAccountScreen") { AdminAccountScreen() }
            }
        }
    }
}