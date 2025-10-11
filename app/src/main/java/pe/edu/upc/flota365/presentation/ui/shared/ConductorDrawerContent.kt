package pe.edu.upc.flota365.presentation.ui.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.ui.theme.Flota365_App_mobileTheme

data class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: String
)

private val drawerItems = listOf(
    DrawerItem(Icons.Outlined.AccountCircle, "Usuario", "route_usuario"),
    DrawerItem(Icons.Outlined.Person, "Rutas", "route_rutas"),
    DrawerItem(Icons.Outlined.Info, "Historial", "route_historial"),
    DrawerItem(Icons.Outlined.Notifications, "Notificaciones", "route_notificaciones"),
    DrawerItem(Icons.Outlined.Call, "Servicio al cliente", "route_servicio_cliente")
)

private val logoutItem = DrawerItem(Icons.AutoMirrored.Filled.ExitToApp, "Cerrar sesión", "route_logout")

/**
 * Contenido para el Navigation Drawer (menú lateral) del conductor.
 *
 * @param onNavigate Callback para manejar la navegación a una ruta específica.
 * @param onCloseDrawer Callback para solicitar el cierre del drawer.
 */
@Composable
fun ConductorDrawerContent(
    onNavigate: (route: String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    val selectedItem = remember { mutableStateOf(drawerItems[0]) }

    ModalDrawerSheet {
        Spacer(Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            drawerItems.forEach { item ->
                NavigationDrawerItem(
                    icon = { Icon(item.icon, contentDescription = item.text) },
                    label = { Text(item.text) },
                    selected = item == selectedItem.value,
                    onClick = {
                        selectedItem.value = item
                        onNavigate(item.route)
                        onCloseDrawer()
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Visual separator content
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            thickness = 1.dp
        )

        // "Cerrar sesión" Item
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            NavigationDrawerItem(
                icon = { Icon(logoutItem.icon, contentDescription = logoutItem.text) },
                label = { Text(logoutItem.text) },
                selected = false,
                onClick = {
                    onNavigate(logoutItem.route)
                    onCloseDrawer()
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Navigation Bar")
@Composable
fun ConductorDrawerContentPreview() {
    Flota365_App_mobileTheme {
        ConductorDrawerContent(
            onNavigate = { },
            onCloseDrawer = { }
        )
    }
}