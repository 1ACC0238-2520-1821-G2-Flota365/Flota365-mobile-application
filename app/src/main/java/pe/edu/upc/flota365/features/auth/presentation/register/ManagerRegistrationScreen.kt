package pe.edu.upc.flota365.features.auth.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.presentation.components.ScaffoldContainer
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton
import pe.edu.upc.flota365.features.auth.data.repositories.ManagerRepositoryImpl
@Composable
fun ManagerRegistrationScreen(
  onBack: () -> Unit,
  onContinue: () -> Unit,
  viewModel: ManagerRegisterViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
  val state by viewModel.state.collectAsState()

  var firstName by rememberSaveable { mutableStateOf("") }
  var lastName by rememberSaveable { mutableStateOf("") }
  var ruc by rememberSaveable { mutableStateOf("") }
  var businessName by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var phone by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }

  ScaffoldContainer(onBack = onBack, title = "Registro - Gestor") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Ingresa la información de tu empresa",
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        ManagerTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombres")
        ManagerTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellidos")
        ManagerTextField(value = ruc, onValueChange = { ruc = it }, label = "RUC")
        ManagerTextField(value = businessName, onValueChange = { businessName = it }, label = "Razón social")
        ManagerTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
        ManagerTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono")
        ManagerTextField(value = password, onValueChange = { password = it }, label = "Contraseña")
      }

      FlotaPrimaryButton(
        text = "Continuar",
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
        modifier = Modifier.fillMaxWidth()
      )
    }

    when (state) {
      is Resource.Success -> onContinue()
      is Resource.Error -> Text(
        text = (state as Resource.Error).message ?: "Error desconocido",
        color = MaterialTheme.colorScheme.error
      )
      is Resource.Loading -> Text(text = "Enviando registro...")
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
      .padding(bottom = 16.dp),
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
