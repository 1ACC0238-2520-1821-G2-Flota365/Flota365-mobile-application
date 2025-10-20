package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import pe.edu.upc.flota365.core.utils.UiState
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme



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

  ScaffoldContainer(onBack = onBack, title = "Acceso a tu cuenta") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Ingrese sus credenciales",
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
          value = email,
          onValueChange = { viewModel.updateEmail(it) },
          label = { Text("Correo electrónico") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = password,
          onValueChange = { viewModel.updatePassword(it) },
          label = { Text("Contraseña") },
          visualTransformation = PasswordVisualTransformation(),
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        if (uiState is UiState.Error) {
          Text(
            text = (uiState as UiState.Error).message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 8.dp)
          )
        }
      }

      Button(
        onClick = { viewModel.login() },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        enabled = email.isNotBlank() && password.isNotBlank()
      ) {
        when (uiState) {
          is UiState.Loading -> CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(20.dp)
          )
          else -> Text("Continuar")
        }
      }
    }
  }
}
@Composable
fun FlotaPrimaryButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    shape = RoundedCornerShape(18.dp)
  ) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
  }
}


@Preview(showBackground = true, showSystemUi = true, name = "Login Form Themed")
@Composable
fun PreviewLoginFormScreenThemed() {
  FlotaTheme {
    LoginFormScreen(
      onBack = {},
      onLoginSuccess = {}
    )
  }
}



