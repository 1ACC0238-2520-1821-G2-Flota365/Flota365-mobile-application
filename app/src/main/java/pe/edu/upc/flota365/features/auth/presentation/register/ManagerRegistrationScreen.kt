package pe.edu.upc.flota365.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Composable
fun ManagerRegistrationScreen(
  onBack: () -> Unit,
  onContinue: () -> Unit,
  viewModel: ManagerRegisterViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  var firstName by rememberSaveable { mutableStateOf("") }
  var lastName by rememberSaveable { mutableStateOf("") }
  var ruc by rememberSaveable { mutableStateOf("") }
  var businessName by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var phone by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }

  val isLoading = state is Resource.Loading
  val isFormValid = listOf(
    firstName,
    lastName,
    ruc,
    businessName,
    email,
    phone,
    password
  ).all { it.isNotBlank() }

  LaunchedEffect(state) {
    if (state is Resource.Success) {
      onContinue()
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

      // HEADER
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(onClick = onBack) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Volver"
          )
        }
        Text(
          text = "Registro",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
          ),
          modifier = Modifier.padding(start = 4.dp)
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // TÍTULO + CHIP
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Gestor de flota",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier
            .background(
              color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
              shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Ingresa la información de tu empresa",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Usaremos estos datos para crear tu cuenta de gestor y configurar tu flota en Flota365.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // CARD CON FORMULARIO
      Card(
        modifier = Modifier
          .weight(1f)
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
            .verticalScroll(rememberScrollState())
        ) {
          ManagerTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = "Nombres"
          )
          ManagerTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Apellidos"
          )
          ManagerTextField(
            value = ruc,
            onValueChange = { ruc = it },
            label = "RUC"
          )
          ManagerTextField(
            value = businessName,
            onValueChange = { businessName = it },
            label = "Razón social"
          )
          ManagerTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico"
          )
          ManagerTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Teléfono"
          )
          ManagerTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña"
          )

          if (state is Resource.Error) {
            Text(
              text = (state as Resource.Error).message ?: "Ocurrió un error al registrar.",
              color = MaterialTheme.colorScheme.error,
              style = MaterialTheme.typography.bodySmall,
              textAlign = TextAlign.Start,
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // BOTÓN + TEXTO INFORMATIVO
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        FlotaPrimaryButton(
          text = if (isLoading) "Enviando..." else "Continuar",
          onClick = {
            viewModel.registerManager(
              firstName = firstName,
              lastName = lastName,
              ruc = ruc,
              businessName = businessName,
              email = email,
              phone = phone,
              password = password
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          enabled = isFormValid && !isLoading,
          isLoading = isLoading
        )

        Text(
          text = "Podrás editar esta información más adelante desde tu perfil de gestor.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
        )
      }
    }
  }
}

@Composable
private fun ManagerTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 12.dp),
    singleLine = true,
    shape = RoundedCornerShape(16.dp)
  )
}

@Preview(showBackground = true, showSystemUi = true, name = "Manager Registration Screen")
@Composable
fun PreviewManagerRegistrationScreen() {
  FlotaTheme {
    ManagerRegistrationScreen(
      onBack = {},
      onContinue = {}
    )
  }
}
