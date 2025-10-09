package pe.edu.upc.flota365.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.presentation.ui.components.AppDrawerHeader
import pe.edu.upc.flota365.presentation.ui.gestor.DashboardScreen
import pe.edu.upc.flota365.presentation.ui.gestor.DriverFormMode
import pe.edu.upc.flota365.presentation.ui.gestor.DriverFormScreen
import pe.edu.upc.flota365.presentation.ui.gestor.DriverStatsScreen
import pe.edu.upc.flota365.presentation.ui.gestor.DriversListScreen
import pe.edu.upc.flota365.presentation.ui.gestor.FleetScreen
import pe.edu.upc.flota365.presentation.ui.gestor.MonitoringScreen
import pe.edu.upc.flota365.presentation.ui.gestor.ReportsScreen
import pe.edu.upc.flota365.presentation.ui.profile.ProfileScreen
import pe.edu.upc.flota365.ui.theme.FlotaTheme

data class DrawerItem(val route: String, val label: String, val icon: @Composable () -> Unit)

@Composable
fun FleetNavGraph() {
  val nav = rememberNavController()
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  val items = listOf(
    DrawerItem(NavDestinations.DASHBOARD, "Dashboard", { Icon(Icons.Filled.Home, null) }),
    DrawerItem(NavDestinations.DRIVERS, "Conductores", { Icon(Icons.Filled.List, null) }),
    DrawerItem(NavDestinations.REPORTS, "Reportes", { Icon(Icons.Filled.Assessment, null) }),
    DrawerItem(NavDestinations.FLEET, "Gestión de flota", { Icon(Icons.Filled.DirectionsCar, null) }),
    DrawerItem(NavDestinations.MONITORING, "Monitoreo", { Icon(Icons.Filled.Map, null) }),
    DrawerItem(NavDestinations.PROFILE, "Perfil", { Icon(Icons.Filled.Person, null) })
  )

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        AppDrawerHeader()
        val current by nav.currentBackStackEntryAsState()
        val currRoute = current?.destination?.route
        items.forEach { item ->
          NavigationDrawerItem(
            label = { Text(item.label) },
            selected = currRoute == item.route,
            onClick = {
              scope.launch { drawerState.close() }
              if (currRoute != item.route) {
                nav.navigate(item.route) {
                  popUpTo(nav.graph.startDestinationId) { saveState = true }
                  launchSingleTop = true
                  restoreState = true
                }
              }
            },
            icon = item.icon,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
          )
        }
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
          onEdit   = { nav.navigate(NavDestinations.DRIVER_EDIT) },
          onStats  = { nav.navigate(NavDestinations.DRIVER_STATS) },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetNavGraphPreview() { FlotaTheme { FleetNavGraph() } }
