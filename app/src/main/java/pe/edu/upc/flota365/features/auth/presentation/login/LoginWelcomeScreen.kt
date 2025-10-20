package pe.edu.upc.flota365.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.components.ScaffoldContainer

@Composable
fun LoginWelcomeScreen(
  onLogin: () -> Unit,
  onCreateAccount: () -> Unit,
  onBack: () -> Unit
) {
  ScaffoldContainer(onBack = onBack, title = "Inicio Sesión") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "Bienvenido a Flota365",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "Gestiona tu flota con herramientas inteligentes y un entorno pensado para conductores y gestores.",
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Column(modifier = Modifier.fillMaxWidth()) {
        FlotaPrimaryButton(
          text = "Iniciar sesión",
          onClick = onLogin,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilledTonalButton(
          onClick = onCreateAccount,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        ) {
          Text("Crear cuenta")
        }
      }
    }
  }
}

/* Login welcome preview */
@Preview(showBackground = true, showSystemUi = true, name = "Login Welcome")
@Composable
fun PreviewLoginWelcomeScreen() {
  FlotaTheme {
    LoginWelcomeScreen(
      onLogin = {},
      onCreateAccount = {},
      onBack = {}
    )
  }
}
