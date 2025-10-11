package pe.edu.upc.flota365.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.flota365.presentation.ui.conductor.DriverRegistrationScreen
import pe.edu.upc.flota365.presentation.ui.gestor.ManagerRegistrationScreen
import pe.edu.upc.flota365.presentation.ui.login.LoginFormScreen
import pe.edu.upc.flota365.presentation.ui.login.LoginWelcomeScreen
import pe.edu.upc.flota365.presentation.ui.login.OnboardingScreen
import pe.edu.upc.flota365.presentation.ui.login.RoleSelectionScreen
import pe.edu.upc.flota365.presentation.ui.subscripcion.PaymentInformationScreen
import pe.edu.upc.flota365.presentation.ui.subscripcion.SubscriptionPlanScreen
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.ui.theme.FlotaTheme


sealed class AppDestination(val route: String) {
  data object Onboarding : AppDestination("onboarding")
  data object LoginWelcome : AppDestination("login_welcome")
  data object LoginForm : AppDestination("login_form")
  data object RoleSelection : AppDestination("role_selection")
  data object RegisterDriver : AppDestination("register_driver")
  data object RegisterManager : AppDestination("register_manager")
  data object SubscriptionPlan : AppDestination("subscription_plan")
  data object PaymentInformation : AppDestination("payment_information")
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
  NavHost(
    navController = navController,
    startDestination = AppDestination.Onboarding.route
  ) {
    composable(AppDestination.Onboarding.route) {
      OnboardingScreen(
        onStart = {
          navController.navigate(AppDestination.LoginWelcome.route)
        }
      )
    }
    composable(AppDestination.LoginWelcome.route) {
      LoginWelcomeScreen(
        onLogin = {
          navController.navigate(AppDestination.LoginForm.route)
        },
        onCreateAccount = {
          navController.navigate(AppDestination.RoleSelection.route)
        },
        onBack = {
          navController.popBackStack()
        }
      )
    }
    composable(AppDestination.LoginForm.route) {
      LoginFormScreen(
        onBack = { navController.popBackStack() },
        onContinue = {
          navController.navigate(AppDestination.RoleSelection.route)
        },
        onGoToSubscription = {
          navController.navigate(AppDestination.SubscriptionPlan.route)
        }
      )
    }
    composable(AppDestination.RoleSelection.route) {
      RoleSelectionScreen(
        onBack = { navController.popBackStack() },
        onDriverSelected = {
          navController.navigate(AppDestination.RegisterDriver.route)
        },
        onManagerSelected = {
          navController.navigate(AppDestination.RegisterManager.route)
        }
      )
    }
    composable(AppDestination.RegisterDriver.route) {
      DriverRegistrationScreen(
        onBack = { navController.popBackStack() },
        onContinue = {
          navController.navigate(AppDestination.SubscriptionPlan.route)
        }
      )
    }
    composable(AppDestination.RegisterManager.route) {
      ManagerRegistrationScreen(
        onBack = { navController.popBackStack() },
        onContinue = {
          navController.navigate(AppDestination.SubscriptionPlan.route)
        }
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
          navController.navigate(AppDestination.Onboarding.route) {
            popUpTo(AppDestination.Onboarding.route) {
              inclusive = true
            }
            launchSingleTop = true
          }
        }
      )
    }
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "App Navigation Preview")
@Composable
fun PreviewAppNavHost() {
  FlotaTheme {
    AppNavHost()
  }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Onboarding.route
    ) {
        composable(AppDestination.Onboarding.route) {
            OnboardingScreen(
                onStart = {
                    navController.navigate(AppDestination.LoginWelcome.route)
                }
            )
        }
        composable(AppDestination.LoginWelcome.route) {
            LoginWelcomeScreen(
                onLogin = {
                    navController.navigate(AppDestination.LoginForm.route)
                },
                onCreateAccount = {
                    navController.navigate(AppDestination.RoleSelection.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppDestination.LoginForm.route) {
            LoginFormScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppDestination.RoleSelection.route)
                },
                onGoToSubscription = {
                    navController.navigate(AppDestination.SubscriptionPlan.route)
                }
            )
        }
        composable(AppDestination.RoleSelection.route) {
            RoleSelectionScreen(
                onBack = { navController.popBackStack() },
                onDriverSelected = {
                    navController.navigate(AppDestination.RegisterDriver.route)
                },
                onManagerSelected = {
                    navController.navigate(AppDestination.RegisterManager.route)
                }
            )
        }
        composable(AppDestination.RegisterDriver.route) {
            DriverRegistrationScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppDestination.SubscriptionPlan.route)
                }
            )
        }
        composable(AppDestination.RegisterManager.route) {
            ManagerRegistrationScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppDestination.SubscriptionPlan.route)
                }
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
                    navController.navigate(AppDestination.Onboarding.route) {
                        popUpTo(AppDestination.Onboarding.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "App Navigation Preview")
@Composable
fun PreviewAppNavHost() {
    Flota365_App_mobileTheme {
        AppNavHost()
    }
}