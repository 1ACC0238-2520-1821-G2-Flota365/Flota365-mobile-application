package pe.edu.upc.flota365.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Suppress("UNUSED_PARAMETER")
@Composable
fun LoginWelcomeScreen(
  onLogin: () -> Unit,
  onCreateAccount: () -> Unit,
  onBack: () -> Unit
) {

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surface
          )
        )
      )
      .padding(horizontal = 24.dp, vertical = 32.dp)
  ) {

    Column(
      modifier = Modifier
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center // TODO: Todo se centra verticalmente
    ) {

      // TEXTOS CENTRADOS AL MEDIO
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Bienvenido a Flota365",
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold
          ),
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Gestiona tu flota con herramientas inteligentes, pensadas para conductores y gestores en movimiento.",
          style = MaterialTheme.typography.bodyLarge,
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(40.dp)) // espacio entre texto y botones

      // BOTONES MÁS ARRIBA (Centrados también)
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

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
          Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Medium
            )
          )
        }
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
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
