package pe.edu.upc.flota365.presentation.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  userName: String = "Usuario",
  onLogout: () -> Unit = {}
) {
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
          text = "Hola, $userName 👋",
          style = MaterialTheme.typography.headlineSmall,
          color = FlotaTextDark
        )

        Button(
          onClick = onLogout,
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
