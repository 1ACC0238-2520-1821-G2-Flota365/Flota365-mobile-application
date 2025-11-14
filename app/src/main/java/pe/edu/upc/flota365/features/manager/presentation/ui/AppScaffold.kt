package pe.edu.upc.flota365.features.manager.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import pe.edu.upc.flota365.core.ui.theme.*
import pe.edu.upc.flota365.features.auth.presentation.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
  title: String,
  onMenuClick: () -> Unit,      // lo dejo en la firma para no romper llamadas,
  onLogout: () -> Unit = {},    // pero ya no se usa el ícono de menú
  snackbarHostState: SnackbarHostState? = null,
  content: @Composable (PaddingValues) -> Unit
) {
  val user = UserSession.currentUser

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = when {
              user != null && user.firstName.isNotBlank() ->
                "Hola, ${user.firstName}"
              user != null && user.fullName.isNotBlank() ->
                "Hola, ${user.fullName.split(" ").first()}"
              else -> title
            },
            color = FlotaBgLight,
            fontWeight = FontWeight.SemiBold
          )
        },
        // 🔹 SIN navigationIcon → desaparecen las 3 rayas
        actions = {
          if (user != null) {
            IconButton(onClick = onLogout) {
              Icon(
                imageVector = Icons.Filled.ExitToApp,
                contentDescription = "Cerrar sesión",
                tint = FlotaBgLight
              )
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = FlotaPrimary,
          titleContentColor = FlotaBgLight,
          navigationIconContentColor = FlotaBgLight,
          actionIconContentColor = FlotaBgLight
        )
      )
    },
    snackbarHost = {
      snackbarHostState?.let {
        SnackbarHost(it) { data ->
          Snackbar(
            snackbarData = data,
            containerColor = FlotaPrimaryAlt,
            contentColor = FlotaTextDark
          )
        }
      }
    },
    containerColor = FlotaGrayLight
  ) { innerPadding ->
    content(innerPadding)
  }
}
