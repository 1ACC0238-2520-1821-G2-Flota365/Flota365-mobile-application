package pe.edu.upc.flota365.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.core.utils.UiState
import pe.edu.upc.flota365.features.auth.presentation.UserSession

@Composable
fun LoginFormScreen(
  onBack: () -> Unit,
  onLoginSuccess: () -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val email by viewModel.email.collectAsState()
  val password by viewModel.password.collectAsState()
  val uiState by viewModel.user.collectAsState()

  LaunchedEffect(uiState) {
    if (uiState is UiState.Success) {
      val user = (uiState as UiState.Success).data
      UserSession.currentUser = user
      onLoginSuccess()
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surface
          )
        )
      )
  ) {

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {

      // Bloque centrado: título + texto + formulario
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Iniciar sesión",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Usa tu correo y contraseña registrados para acceder a Flota365.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
          modifier = Modifier
            .fillMaxWidth(),
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 16.dp)
          ) {

            OutlinedTextField(
              value = email,
              onValueChange = { viewModel.updateEmail(it) },
              label = { Text("Correo electrónico") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val passwordVisible = remember { mutableStateOf(false) }

            OutlinedTextField(
              value = password,
              onValueChange = { viewModel.updatePassword(it) },
              label = { Text("Contraseña") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(16.dp),
              visualTransformation = if (passwordVisible.value) {
                VisualTransformation.None
              } else {
                PasswordVisualTransformation()
              },
              trailingIcon = {
                val icon = if (passwordVisible.value) {
                  Icons.Filled.VisibilityOff
                } else {
                  Icons.Filled.Visibility
                }
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                  Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              },
              isError = uiState is UiState.Error
            )

            if (uiState is UiState.Error) {
              Text(
                text = (uiState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Botón + políticas abajo
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        FlotaPrimaryButton(
          text = "Continuar",
          onClick = { viewModel.login() },
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          enabled = email.isNotBlank() && password.isNotBlank(),
          isLoading = uiState is UiState.Loading
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Al continuar aceptas las políticas de uso de Flota365.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@Composable
fun FlotaPrimaryButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  isLoading: Boolean = false
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled && !isLoading,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    shape = RoundedCornerShape(18.dp)
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.size(20.dp),
        strokeWidth = 2.dp
      )
    } else {
      Text(text, style = MaterialTheme.typography.titleMedium)
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginFormScreenThemed() {
  FlotaTheme {
    LoginFormScreen(
      onBack = {},
      onLoginSuccess = {}
    )
  }
}
