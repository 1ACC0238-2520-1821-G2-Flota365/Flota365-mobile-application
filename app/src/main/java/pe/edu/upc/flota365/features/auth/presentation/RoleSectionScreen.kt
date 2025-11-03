package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.components.RoleCard
import pe.edu.upc.flota365.features.auth.presentation.components.ScaffoldContainer

@Composable
fun RoleSelectionScreen(
  onBack: () -> Unit,
  onDriverSelected: () -> Unit,
  onManagerSelected: () -> Unit
) {
  ScaffoldContainer(onBack = onBack, title = "Selecciona tu rol") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "¿Quién eres?",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 24.dp)
      )
      RoleCard(
        title = "Gestor",
        description = "Administra flotas, pagos y reportes en tiempo real.",
        onClick = onManagerSelected
      )
    }
  }
}

/* Role selection preview */
@Preview(showBackground = true, showSystemUi = true, name = "Role Selection")
@Composable
fun PreviewRoleSelectionScreen() {
  FlotaTheme {
    RoleSelectionScreen(
      onBack = {},
      onDriverSelected = {},
      onManagerSelected = {}
    )
  }
}
