package pe.edu.upc.flota365.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.presentation.ui.gestor.*
import pe.edu.upc.flota365.presentation.ui.profile.ProfileScreen
import pe.edu.upc.flota365.ui.theme.FlotaTheme

data class DrawerItem(
  val route: String? = null,
  val label: String,
  val icon: @Composable () -> Unit
)

@Composable
fun FleetNavGraph() {
  val nav = rememberNavController()
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  // Ítems en el mismo orden y nombres del mock
  val drawerItems = listOf(
    DrawerItem(NavDestinations.PROFILE, "Usuario") { Icon(Icons.Filled.Person, null) },
    DrawerItem(NavDestinations.DASHBOARD, "Dashboard") { Icon(Icons.Filled.Home, null) },
    DrawerItem(NavDestinations.DRIVERS, "Conductores") { Icon(Icons.Filled.List, null) },
    DrawerItem(NavDestinations.FLEET, "Gestión de Flota") { Icon(Icons.Filled.DirectionsCar, null) },
    DrawerItem(NavDestinations.REPORTS, "Reportes") { Icon(Icons.Filled.Assessment, null) },
    DrawerItem(NavDestinations.MONITORING, "Monitoreo") { Icon(Icons.Filled.Map, null) },
    DrawerItem(null, "Notificaciones") { Icon(Icons.Filled.Notifications, null) },     // placeholder
    DrawerItem(null, "Servicio al cliente") { Icon(Icons.Filled.HeadsetMic, null) },   // placeholder
  )

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      // Hoja del drawer tipo “sheet” como en la imagen
      ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.width(300.dp) // ancho cómodo para móvil
      ) {
        DrawerHeader() // avatar + título “Usuario”

        val current by nav.currentBackStackEntryAsState()
        val currRoute = current?.destination?.route

        DrawerList(
          items = drawerItems,
          selectedRoute = currRoute,
          onItemClick = { item ->
            scope.launch { drawerState.close() }
            item.route?.let { route ->
              if (route != currRoute) {
                nav.navigate(route) {
                  popUpTo(nav.graph.startDestinationId) { saveState = true }
                  launchSingleTop = true
                  restoreState = true
                }
              }
            }
          }
        )

        // Separador y “Cerrar sesión” abajo
        Spacer(Modifier.weight(1f))
        Divider()
        DrawerLogoutRow(
          onClick = {
            // TODO: lógica de cierre de sesión
            scope.launch { drawerState.close() }
          }
        )
        Spacer(Modifier.height(8.dp))
      }
    }
  ) {
    NavHost(
      navController = nav,
      startDestination = NavDestinations.DASHBOARD
    ) {
      composable(NavDestinations.DASHBOARD) {
        DashboardScreen(onMenuClick = { scope.launch { drawerState.open() } })
      }
      composable(NavDestinations.DRIVERS) {
        DriversListScreen(
          onCreate = { nav.navigate(NavDestinations.DRIVER_CREATE) },
          onEdit =   { nav.navigate(NavDestinations.DRIVER_EDIT) },
          onStats =  { nav.navigate(NavDestinations.DRIVER_STATS) },
          onMenuClick = { scope.launch { drawerState.open() } }
        )
      }
      composable(NavDestinations.DRIVER_CREATE) { DriverFormScreen(DriverFormMode.Create) }
      composable(NavDestinations.DRIVER_EDIT)   { DriverFormScreen(DriverFormMode.Edit) }
      composable(NavDestinations.DRIVER_STATS)  { DriverStatsScreen() }
      composable(NavDestinations.REPORTS)       { ReportsScreen() }
      composable(NavDestinations.FLEET)         { FleetScreen() }
      composable(NavDestinations.MONITORING)    { MonitoringScreen() }
      composable(NavDestinations.PROFILE)       { ProfileScreen() }
    }
  }
}

/* ----------------------------- Drawer UI ----------------------------- */

@Composable
private fun DrawerHeader() {
  // Encabezado sencillo como en la captura: avatar y texto “Usuario”
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
    horizontalAlignment = Alignment.Start
  ) {
    val chipShape: Shape = MaterialTheme.shapes.large
    Surface(
      color = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier.size(44.dp).clip(chipShape),
      tonalElevation = 0.dp,
      shadowElevation = 0.dp
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(Icons.Filled.Person, contentDescription = null)
      }
    }
    Spacer(Modifier.height(10.dp))
    Text("Usuario", style = MaterialTheme.typography.titleMedium)
  }
  Divider()
}

@Composable
private fun DrawerList(
  items: List<DrawerItem>,
  selectedRoute: String?,
  onItemClick: (DrawerItem) -> Unit
) {
  LazyColumn(
    contentPadding = PaddingValues(vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(2.dp)
  ) {
    items(items) { item ->
      DrawerRow(
        label = item.label,
        leading = item.icon,
        selected = (item.route != null && item.route == selectedRoute),
        onClick = { onItemClick(item) }
      )
    }
  }
}

@Composable
private fun DrawerRow(
  label: String,
  leading: @Composable () -> Unit,
  selected: Boolean,
  onClick: () -> Unit
) {
  // Estilo similar a la imagen: icono en círculo turquesa + texto
  val circleColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
  val iconTint   = MaterialTheme.colorScheme.primary
  val container  = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
  else MaterialTheme.colorScheme.surface

  Surface(
    color = container,
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = 48.dp)
      .padding(horizontal = 8.dp)
      .clip(MaterialTheme.shapes.medium),
    onClick = onClick
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        color = circleColor,
        shape = MaterialTheme.shapes.large
      ) {
        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
          CompositionLocalProvider(LocalContentColor provides iconTint) {
            leading()
          }
        }
      }
      Spacer(Modifier.width(14.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun DrawerLogoutRow(onClick: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
      shape = MaterialTheme.shapes.large
    ) {
      Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
        Icon(Icons.Filled.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
      }
    }
    Spacer(Modifier.width(14.dp))
    TextButton(onClick = onClick) {
      Text("Cerrar sesión")
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetNavGraphPreview() { FlotaTheme { FleetNavGraph() } }
