package pe.edu.upc.flota365.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import pe.edu.upc.flota365.core.ui.theme.*
import pe.edu.upc.flota365.features.manager.presentation.driver.*
import pe.edu.upc.flota365.features.manager.presentation.manager.FleetScreen
import pe.edu.upc.flota365.features.manager.presentation.monitoring.MonitoringScreen
import pe.edu.upc.flota365.features.manager.presentation.report.CreateReportScreen
import pe.edu.upc.flota365.features.manager.presentation.report.ReportsScreen
import pe.edu.upc.flota365.features.manager.presentation.screens.DashboardScreen
import pe.edu.upc.flota365.features.manager.presentation.viewmodel.ManagerViewModel
import pe.edu.upc.flota365.presentation.ui.profile.ProfileScreen

/* ----------------------------- NAV DESTINATIONS ----------------------------- */
object NavDestinations {
  const val DASHBOARD = "dashboard"
  const val DRIVERS = "drivers"
  const val FLEET = "fleet"
  const val MONITORING = "monitoring"
  const val REPORTS = "reports"
  const val PROFILE = "profile"

  const val DRIVER_CREATE = "driver_create"
  const val DRIVER_EDIT = "driver_edit"
  const val DRIVER_STATS = "driver_stats"
  const val REPORT_CREATE = "report_create"
}

/* ----------------------------- NAV GRAPH ----------------------------- */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FleetNavGraph(rootNavController: NavHostController) {
  val navController = rememberNavController()

  Scaffold(
    bottomBar = { BottomNavigationBar(navController) },
    containerColor = FlotaGrayLight
  ) { padding ->
    NavHost(
      navController = navController,
      startDestination = NavDestinations.DASHBOARD,
      modifier = Modifier.padding(padding)
    ) {
      /* --------- Dashboard --------- */
      composable(NavDestinations.DASHBOARD) {
        val viewModel: ManagerViewModel = hiltViewModel()
        DashboardScreen(viewModel = viewModel)
      }

      /* --------- Drivers --------- */
      composable(NavDestinations.DRIVERS) {
        DriversListScreen(
          onCreate = { navController.navigate(NavDestinations.DRIVER_CREATE) },
          onEdit = { navController.navigate(NavDestinations.DRIVER_EDIT) },
          onStats = { navController.navigate(NavDestinations.DRIVER_STATS) },
          onMenuClick = {}
        )
      }
      composable(NavDestinations.DRIVER_CREATE) { DriverFormScreen(DriverFormMode.Create) }
      composable(NavDestinations.DRIVER_EDIT) { DriverFormScreen(DriverFormMode.Edit) }
      composable(NavDestinations.DRIVER_STATS) { DriverStatsScreen() }

      /* --------- Fleet --------- */
      composable(NavDestinations.FLEET) { FleetScreen() }

      /* --------- Monitoring --------- */
      composable(NavDestinations.MONITORING) {
        val viewModel: ManagerViewModel = hiltViewModel()
        MonitoringScreen(viewModel = viewModel, onMenuClick = {})
      }

      /* --------- Reports --------- */
      composable(NavDestinations.REPORTS) {
        ReportsScreen(onNavigateToCreate = { navController.navigate(NavDestinations.REPORT_CREATE) })
      }
      composable(NavDestinations.REPORT_CREATE) {
        CreateReportScreen(
          onReportCreated = { navController.popBackStack() },
          onCancel = { navController.popBackStack() }
        )
      }

      /* --------- Profile --------- */
      composable(NavDestinations.PROFILE) {
        ProfileScreen()
      }
    }
  }
}

/* ----------------------------- Bottom Navigation ----------------------------- */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
  val items = listOf(
    BottomNavItem("Dashboard", Icons.Filled.Home, NavDestinations.DASHBOARD),
    BottomNavItem("Conductores", Icons.Filled.List, NavDestinations.DRIVERS),
    BottomNavItem("Flota", Icons.Filled.DirectionsCar, NavDestinations.FLEET),
    BottomNavItem("Monitoreo", Icons.Filled.Map, NavDestinations.MONITORING),
    BottomNavItem("Reportes", Icons.Filled.Assessment, NavDestinations.REPORTS),
    BottomNavItem("Perfil", Icons.Filled.Person, NavDestinations.PROFILE)
  )

  NavigationBar(
    containerColor = FlotaPrimary,
    tonalElevation = 4.dp
  ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    items.forEach { item ->
      NavigationBarItem(
        icon = { Icon(item.icon, contentDescription = item.label) },
        label = { Text(item.label, color = FlotaBgLight) },
        selected = currentRoute == item.route,
        onClick = {
          navController.navigate(item.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = FlotaBgLight,
          selectedTextColor = FlotaBgLight,
          unselectedIconColor = FlotaPrimaryAlt,
          indicatorColor = FlotaPrimaryAlt
        )
      )
    }
  }
}

/* ----------------------------- Helper ----------------------------- */
data class BottomNavItem(
  val label: String,
  val icon: androidx.compose.ui.graphics.vector.ImageVector,
  val route: String
)

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
