package pe.edu.upc.flota365.features.manager.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
import pe.edu.upc.flota365.features.manager.presentation.monitoring.MonitoringViewModel
import pe.edu.upc.flota365.features.manager.presentation.report.CreateReportScreen
import pe.edu.upc.flota365.features.manager.presentation.report.ReportsScreen
import pe.edu.upc.flota365.features.manager.presentation.screens.DashboardScreen
import pe.edu.upc.flota365.features.manager.presentation.viewmodel.ManagerViewModel
import pe.edu.upc.flota365.features.manager.presentation.profile.ProfileScreen

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

/* ----------------------------- MAIN NAV GRAPH ----------------------------- */
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
      // Dashboard
      composable(NavDestinations.DASHBOARD) {
        val viewModel: ManagerViewModel = hiltViewModel()
        DashboardScreen(
          viewModel = viewModel,
          onNavigateToProfile = { navController.navigate(NavDestinations.PROFILE) }
        )
      }

      // Conductores
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

      // Flota
      composable(NavDestinations.FLEET) { FleetScreen() }

      // Monitoreo
// Monitoreo
      composable(NavDestinations.MONITORING) {
        val viewModel: MonitoringViewModel = hiltViewModel()
        MonitoringScreen(viewModel = viewModel, onMenuClick = {})
      }
      // Reportes
      composable(NavDestinations.REPORTS) {
        ReportsScreen(onNavigateToCreate = { navController.navigate(NavDestinations.REPORT_CREATE) })
      }
      composable(NavDestinations.REPORT_CREATE) {
        CreateReportScreen(
          onReportCreated = { navController.popBackStack() },
          onCancel = { navController.popBackStack() }
        )
      }

      // Perfil (solo accesible desde el dashboard)
      composable(NavDestinations.PROFILE) {
        ProfileScreen(navController = rootNavController)
      }
    }
  }
}

/* ----------------------------- BOTTOM NAVIGATION ----------------------------- */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
  val items = listOf(
    BottomNavItem("Inicio", Icons.Filled.Home, NavDestinations.DASHBOARD),
    BottomNavItem("Conductores", Icons.Filled.List, NavDestinations.DRIVERS),
    BottomNavItem("Flota", Icons.Filled.DirectionsCar, NavDestinations.FLEET),
    BottomNavItem("Monitoreo", Icons.Filled.Map, NavDestinations.MONITORING),
    BottomNavItem("Reportes", Icons.Filled.Assessment, NavDestinations.REPORTS)
  )

  NavigationBar(
    containerColor = FlotaBgLight,
    tonalElevation = 5.dp
  ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    items.forEach { item ->
      val selected = currentRoute == item.route
      val iconColor by animateColorAsState(
        if (selected) FlotaPrimary else FlotaTextDark,
        label = "iconColorAnim"
      )

      NavigationBarItem(
        icon = {
          Icon(
            item.icon,
            contentDescription = item.label,
            tint = iconColor
          )
        },
        label = {
          Text(
            text = item.label,
            color = iconColor,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            style = LocalTextStyle.current.copy(fontSize = MaterialTheme.typography.labelSmall.fontSize)
          )
        },
        selected = selected,
        onClick = {
          navController.navigate(item.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        },
        colors = NavigationBarItemDefaults.colors(
          indicatorColor = FlotaPrimaryAlt.copy(alpha = 0.15f)
        )
      )
    }
  }
}

/* ----------------------------- NAV ITEM MODEL ----------------------------- */
data class BottomNavItem(
  val label: String,
  val icon: ImageVector,
  val route: String
)

/* ----------------------------- PREVIEW ----------------------------- */
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetNavGraphPreview() {
  val navController = rememberNavController()
  FlotaTheme {
    FleetNavGraph(rootNavController = navController)
  }
}
