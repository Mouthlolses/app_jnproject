package com.caririfest.admin.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AdminDrawer(
    drawerState: DrawerState,
    selectedItem: String,
    onItemClick: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val items = listOf(
        DrawerItem("Início", Icons.Outlined.Home, route = "adminScreen"),
        DrawerItem("Métricas", Icons.Outlined.Memory, route = "metricScreen"),
        DrawerItem("Sair", Icons.AutoMirrored.Outlined.Logout, route = "auth")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(260.dp)
            ) {

                // Categoria
                Text(
                    text = "Hub do Produtor",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(20.dp)
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                // Lista
                items.forEach { item ->
                    val isSelected = item.route == selectedItem

                    NavigationDrawerItem(
                        label = {
                            Text(item.title)
                        },
                        selected = isSelected,
                        onClick = { onItemClick(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFFE8E0F8), // lilás claro igual da imagem
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = Color(0xFF1D192B),
                        )
                    )
                }
            }
        },
        content = content
    )
}

data class DrawerItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)