package pe.edu.upc.flota365.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.features.auth.presentation.UserSession
import pe.edu.upc.flota365.presentation.ui.profile.ProfileScreen
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.manager.presentation.DashboardScreen
import pe.edu.upc.flota365.features.manager.presentation.driver.DriverFormMode
import pe.edu.upc.flota365.features.manager.presentation.driver.DriverFormScreen
import pe.edu.upc.flota365.features.manager.presentation.driver.DriverStatsScreen
import pe.edu.upc.flota365.features.manager.presentation.driver.DriversListScreen
import pe.edu.upc.flota365.features.manager.presentation.manager.FleetScreen
import pe.edu.upc.flota365.features.manager.presentation.monitoring.MonitoringScreen
import pe.edu.upc.flota365.features.manager.presentation.report.CreateReportScreen
import pe.edu.upc.flota365.features.manager.presentation.report.ReportsScreen

/* ----------------------------- NAV DESTINATIONS ----------------------------- */
object NavDestinations {
  const val DASHBOARD = "dashboard"
  const val DRIVERS = "drivers"
  const val DRIVER_CREATE = "driver_create"
  const val DRIVER_EDIT = "driver_edit"
  const val DRIVER_STATS = "driver_stats"
  const val FLEET = "fleet"
  const val REPORTS = "reports"
  const val MONITORING = "monitoring"
  const val PROFILE = "profile"

  const val REPORT_CREATE = "report_create"
}

/* ----------------------------- NAV GRAPH ----------------------------- */
@RequiresApi(Build.VERSION_CODES.O)
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

        // Items del menú lateral
        val drawerItems = listOf(
          Triple("Dashboard", Icons.Filled.Home, NavDestinations.DASHBOARD),
          Triple("Conductores", Icons.Filled.List, NavDestinations.DRIVERS),
          Triple("Gestión de flota", Icons.Filled.DirectionsCar, NavDestinations.FLEET),
          Triple("Monitoreo", Icons.Filled.Map, NavDestinations.MONITORING),
          Triple("Reportes", Icons.Filled.Assessment, NavDestinations.REPORTS),
          Triple("Perfil", Icons.Filled.Person, NavDestinations.PROFILE),
        )

        drawerItems.forEach { (label, icon, route) ->
          NavigationDrawerItem(
            label = { Text(label) },
            selected = false,
            onClick = {
              navController.navigate(route)
              scope.launch { drawerState.close() }
            },
            icon = { Icon(icon, null) }
          )
        }

        Spacer(Modifier.height(12.dp))
        Divider()
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

      composable(NavDestinations.FLEET) {
        FleetScreen()
      }

      composable(NavDestinations.REPORTS) {
        ReportsScreen(
          onNavigateToCreate = { navController.navigate(NavDestinations.REPORT_CREATE) }
        )
      }

      composable(NavDestinations.MONITORING) {
        MonitoringScreen(onMenuClick = { scope.launch { drawerState.open() } })
      }

      composable(NavDestinations.PROFILE) {
        ProfileScreen()
      }

      composable(NavDestinations.REPORT_CREATE) {
        CreateReportScreen(
          onReportCreated = {
            navController.popBackStack() // vuelve a ReportsScreen
          },
          onCancel = {
            navController.popBackStack()
          }
        )
      }
    }
  }
}

/* ----------------------------- Drawer Header ----------------------------- */
@Composable
fun DrawerHeader() {
  val user = UserSession.currentUser

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalAlignment = Alignment.Start
  ) {
    Surface(
      color = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier.size(48.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(Icons.Default.Person, contentDescription = null)
      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = user?.fullName ?: "Invitado",
      style = MaterialTheme.typography.titleMedium
    )
    Text(
      text = user?.email ?: "",
      style = MaterialTheme.typography.bodySmall
    )
  }
}

/* ----------------------------- Logout Button ----------------------------- */
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
          UserSession.currentUser = null
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
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetNavGraphPreview() {
  val navController = rememberNavController()
  FlotaTheme {
    FleetNavGraph(rootNavController = navController)
  }
}
