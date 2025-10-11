package pe.edu.upc.flota365.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
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
fun FleetNavGraph(rootNavController: NavHostController) {
  val navController = rememberNavController()
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        DrawerHeader()

        NavigationDrawerItem(
          label = { Text("Usuario") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.PROFILE)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.Person, null) }
        )
        NavigationDrawerItem(
          label = { Text("Dashboard") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.DASHBOARD)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.Home, null) }
        )
        NavigationDrawerItem(
          label = { Text("Conductores") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.DRIVERS)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.List, null) }
        )
        NavigationDrawerItem(
          label = { Text("Gestión de flota") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.FLEET)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.DirectionsCar, null) }
        )
        NavigationDrawerItem(
          label = { Text("Reportes") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.REPORTS)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.Assessment, null) }
        )
        NavigationDrawerItem(
          label = { Text("Monitoreo") },
          selected = false,
          onClick = {
            navController.navigate(NavDestinations.MONITORING)
            scope.launch { drawerState.close() }
          },
          icon = { Icon(Icons.Filled.Map, null) }
        )

        Spacer(Modifier.height(12.dp))
        Divider()
        // ✅ Botón de cerrar sesión
        DrawerLogoutRow(scope, drawerState, rootNavController)
      }
    }
  ) {
    NavHost(
      navController = navController,
      startDestination = NavDestinations.DASHBOARD
    ) {
      composable(NavDestinations.DASHBOARD) {
        DashboardScreen(onMenuClick = { scope.launch { drawerState.open() } })
      }

      composable(NavDestinations.DRIVERS) {
        DriversListScreen(
          onCreate = { navController.navigate(NavDestinations.DRIVER_CREATE) },
          onEdit = { navController.navigate(NavDestinations.DRIVER_EDIT) },
          onStats = { navController.navigate(NavDestinations.DRIVER_STATS) },
          onMenuClick = { scope.launch { drawerState.open() } }
        )
      }

      composable(NavDestinations.DRIVER_CREATE) {
        DriverFormScreen(DriverFormMode.Create)
      }
      composable(NavDestinations.DRIVER_EDIT) {
        DriverFormScreen(DriverFormMode.Edit)
      }
      composable(NavDestinations.DRIVER_STATS) {
        DriverStatsScreen()
      }

      composable(NavDestinations.REPORTS) { ReportsScreen() }
      composable(NavDestinations.FLEET) { FleetScreen() }

      composable(NavDestinations.MONITORING) {
        MonitoringScreen(onMenuClick = { scope.launch { drawerState.open() } })
      }

      composable(NavDestinations.PROFILE) {
        ProfileScreen()
      }
    }
  }
}

/* ----------------------------- Drawer UI ----------------------------- */

@Composable
private fun DrawerHeader() {
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
      modifier = Modifier
        .size(44.dp)
        .clip(chipShape),
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
private fun DrawerLogoutRow(
  scope: CoroutineScope,
  drawerState: DrawerState,
  rootNavController: NavHostController
) {
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
      Box(
        modifier = Modifier.size(36.dp),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          Icons.Filled.Logout,
          contentDescription = "Cerrar sesión",
          tint = MaterialTheme.colorScheme.primary
        )
      }
    }

    Spacer(Modifier.width(14.dp))

    TextButton(
      onClick = {
        scope.launch {
          drawerState.close()
          rootNavController.navigate(AppDestination.LoginWelcome.route) {
            popUpTo(AppDestination.FleetMain.route) { inclusive = true }
            launchSingleTop = true
          }
        }
      }
    ) {
      Text("Cerrar sesión")
    }
  }
}

/* ----------------------------- Preview ----------------------------- */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetNavGraphPreview() {
  val fakeRootNavController = rememberNavController()
  FlotaTheme {
    FleetNavGraph(rootNavController = fakeRootNavController)
  }
}
