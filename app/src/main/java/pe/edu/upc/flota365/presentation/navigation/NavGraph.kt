package pe.edu.upc.flota365.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.presentation.ui.components.AppDrawerHeader
import pe.edu.upc.flota365.presentation.ui.conductor.DriverRegistrationScreen
import pe.edu.upc.flota365.presentation.ui.gestor.*
import pe.edu.upc.flota365.presentation.ui.login.*
import pe.edu.upc.flota365.presentation.ui.subscripcion.*
import pe.edu.upc.flota365.ui.theme.FlotaTheme

// -------------------------
//  RUTAS PRINCIPALES
// -------------------------
sealed class AppDestination(val route: String) {
  object Onboarding : AppDestination("onboarding")
  object LoginWelcome : AppDestination("login_welcome")
  object LoginForm : AppDestination("login_form")
  object RoleSelection : AppDestination("role_selection")
  object RegisterDriver : AppDestination("register_driver")
  object RegisterManager : AppDestination("register_manager")
  object SubscriptionPlan : AppDestination("subscription_plan")
  object PaymentInformation : AppDestination("payment_information")
  object MainApp : AppDestination("main_app")
}

// -------------------------
//  RUTAS DEL DASHBOARD
// -------------------------
object AppNavDestinations {
  const val DASHBOARD = "dashboard"
  const val DRIVERS = "drivers"
  const val DRIVER_CREATE = "driver_create"
  const val DRIVER_EDIT = "driver_edit"
  const val DRIVER_STATS = "driver_stats"
  const val REPORTS = "reports"
  const val FLEET = "fleet"
  const val MONITORING = "monitoring"
  const val PROFILE = "profile"
}

// -------------------------
//  MODELO PARA DRAWER
// -------------------------
data class DrawerItem(val route: String, val label: String, val icon: @Composable () -> Unit)

// -------------------------
//  FLUJO PRINCIPAL DE NAVEGACIÓN
// -------------------------
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
  NavHost(
    navController = navController,
    startDestination = AppDestination.Onboarding.route
  ) {
    // ------------------- AUTENTICACIÓN Y SUSCRIPCIÓN -------------------
    composable(AppDestination.Onboarding.route) {
      OnboardingScreen(onStart = { navController.navigate(AppDestination.LoginWelcome.route) })
    }
    composable(AppDestination.LoginWelcome.route) {
      LoginWelcomeScreen(
        onLogin = { navController.navigate(AppDestination.LoginForm.route) },
        onCreateAccount = { navController.navigate(AppDestination.RoleSelection.route) },
        onBack = { navController.popBackStack() }
      )
    }
    composable(AppDestination.LoginForm.route) {
      LoginFormScreen(
        onBack = { navController.popBackStack() },
        onContinue = {
          navController.navigate(AppDestination.MainApp.route) {
            popUpTo(AppDestination.Onboarding.route) { inclusive = true }
          }
        },
        onGoToSubscription = {
          navController.navigate(AppDestination.SubscriptionPlan.route)
        }
      )
    }
    composable(AppDestination.RoleSelection.route) {
      RoleSelectionScreen(
        onBack = { navController.popBackStack() },
        onDriverSelected = { navController.navigate(AppDestination.RegisterDriver.route) },
        onManagerSelected = { navController.navigate(AppDestination.RegisterManager.route) }
      )
    }
    composable(AppDestination.RegisterDriver.route) {
      DriverRegistrationScreen(
        onBack = { navController.popBackStack() },
        onContinue = { navController.navigate(AppDestination.SubscriptionPlan.route) }
      )
    }
    composable(AppDestination.RegisterManager.route) {
      ManagerRegistrationScreen(
        onBack = { navController.popBackStack() },
        onContinue = { navController.navigate(AppDestination.SubscriptionPlan.route) }
      )
    }
    composable(AppDestination.SubscriptionPlan.route) {
      SubscriptionPlanScreen(
        onBack = { navController.popBackStack() },
        onSelectPlan = { plan ->
          navController.currentBackStackEntry?.savedStateHandle?.set("selectedPlanName", plan.name)
          navController.currentBackStackEntry?.savedStateHandle?.set("selectedPlanPrice", plan.price)
          navController.navigate(AppDestination.PaymentInformation.route)
        }
      )
    }
    composable(AppDestination.PaymentInformation.route) {
      val previousEntry = navController.previousBackStackEntry
      val planName = previousEntry?.savedStateHandle?.get<String>("selectedPlanName") ?: "Plan Premium"
      val planPrice = previousEntry?.savedStateHandle?.get<String>("selectedPlanPrice") ?: "S/67.89"

      PaymentInformationScreen(
        onBack = { navController.popBackStack() },
        planName = planName,
        planPrice = planPrice,
        onSubscriptionConfirmed = {
          navController.navigate(AppDestination.MainApp.route) {
            popUpTo(AppDestination.Onboarding.route) { inclusive = true }
          }
        }
      )
    }

    // ------------------- DASHBOARD (MAIN APP) -------------------
    composable(AppDestination.MainApp.route) {
      DashboardNavGraph()
    }
  }
}

// -------------------------
//  NAVEGACIÓN CON DRAWER
// -------------------------
@Composable
fun DashboardNavGraph() {
  val nav = rememberNavController()
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  val items = listOf(
    DrawerItem(AppNavDestinations.DASHBOARD, "Dashboard") { Icon(Icons.Filled.Home, null) },
    DrawerItem(AppNavDestinations.DRIVERS, "Conductores") { Icon(Icons.Filled.List, null) },
    DrawerItem(AppNavDestinations.REPORTS, "Reportes") { Icon(Icons.Filled.Assessment, null) },
    DrawerItem(AppNavDestinations.FLEET, "Gestión de flota") { Icon(Icons.Filled.DirectionsCar, null) },
    DrawerItem(AppNavDestinations.MONITORING, "Monitoreo") { Icon(Icons.Filled.Map, null) },
    DrawerItem(AppNavDestinations.PROFILE, "Perfil") { Icon(Icons.Filled.Person, null) }
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
    NavHost(navController = nav, startDestination = AppNavDestinations.DASHBOARD) {
      composable(AppNavDestinations.DASHBOARD) { DashboardScreen(onMenuClick = { scope.launch { drawerState.open() } }) }
      composable(AppNavDestinations.DRIVERS) {
        DriversListScreen(
          onCreate = { nav.navigate(AppNavDestinations.DRIVER_CREATE) },
          onEdit = { nav.navigate(AppNavDestinations.DRIVER_EDIT) },
          onStats = { nav.navigate(AppNavDestinations.DRIVER_STATS) },
          onMenuClick = { scope.launch { drawerState.open() } }
        )
      }
      composable(AppNavDestinations.DRIVER_CREATE) { DriverFormScreen(DriverFormMode.Create) }
      composable(AppNavDestinations.DRIVER_EDIT) { DriverFormScreen(DriverFormMode.Edit) }
      composable(AppNavDestinations.DRIVER_STATS) { DriverStatsScreen() }
      composable(AppNavDestinations.REPORTS) { ReportsScreen() }
      composable(AppNavDestinations.FLEET) { FleetScreen() }
      composable(AppNavDestinations.MONITORING) { MonitoringScreen() }
      composable(AppNavDestinations.PROFILE) { ProfileScreen() }
    }
  }
}

// -------------------------
//  PANTALLAS MOCK (para evitar errores de importación)
// -------------------------
@Composable fun DriversListScreen(onCreate: () -> Unit, onEdit: () -> Unit, onStats: () -> Unit, onMenuClick: () -> Unit) {}
@Composable fun DriverStatsScreen() {}
@Composable fun MonitoringScreen() {}
@Composable fun ProfileScreen() {}

// -------------------------
//  PREVIEW
// -------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNavGraph() {
  FlotaTheme { NavGraph() }
}
