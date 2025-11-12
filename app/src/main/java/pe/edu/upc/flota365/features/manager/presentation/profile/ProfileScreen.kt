package pe.edu.upc.flota365.features.manager.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.ui.theme.*
import pe.edu.upc.flota365.features.auth.presentation.UserSession
import pe.edu.upc.flota365.features.auth.presentation.navigation.AppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  navController: NavController,
  userName: String = UserSession.currentUser?.firstName ?: "Usuario"
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Perfil",
            fontWeight = FontWeight.Bold,
            color = FlotaBgLight
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FlotaPrimary)
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    containerColor = FlotaGrayLight
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        Text(
          text = "Hola, $userName",
          style = MaterialTheme.typography.headlineSmall,
          color = FlotaTextDark
        )

        Button(
          onClick = {
            scope.launch {
              // 🔹 Limpia la sesión
              UserSession.clear()

              // 🔹 Muestra feedback visual
              snackbarHostState.showSnackbar("Sesión cerrada correctamente")

              // 🔹 Pequeña pausa antes de redirigir
              delay(800)

              // 🔹 Regresa al flujo de login y limpia el backstack
              navController.navigate(AppDestination.LoginWelcome.route) {
                popUpTo(AppDestination.Onboarding.route) { inclusive = true }
                launchSingleTop = true
              }
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = FlotaError)
        ) {
          Icon(
            Icons.Filled.ExitToApp,
            contentDescription = "Cerrar sesión",
            tint = FlotaBgLight
          )
          Spacer(Modifier.width(8.dp))
          Text("Cerrar Sesión", color = FlotaBgLight)
        }
      }
    }
  }
}
