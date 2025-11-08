package pe.edu.upc.flota365.features.manager.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pe.edu.upc.flota365.features.auth.presentation.UserSession

/**
 * Scaffold base con TopAppBar que muestra el ícono de menú y saludo del usuario.
 * Incluye botón de cerrar sesión y soporte para Snackbars.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
  title: String,
  onMenuClick: () -> Unit,
  onLogout: () -> Unit = {},
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
            }
          )
        },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(Icons.Filled.Menu, contentDescription = "Abrir menú")
          }
        },
        actions = {
          if (user != null) {
            IconButton(onClick = onLogout) {
              Icon(Icons.Filled.ExitToApp, contentDescription = "Cerrar sesión")
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors()
      )
    },
    snackbarHost = {
      snackbarHostState?.let { SnackbarHost(it) }
    }
  ) { innerPadding ->
    content(innerPadding)
  }
}
