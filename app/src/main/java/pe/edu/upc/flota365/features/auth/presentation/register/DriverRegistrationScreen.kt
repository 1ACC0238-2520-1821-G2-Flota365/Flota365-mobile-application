package pe.edu.upc.flota365.features.auth.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.components.DriverTextField
import pe.edu.upc.flota365.features.auth.presentation.components.ScaffoldContainer
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Composable
fun DriverRegistrationScreen(
  onBack: () -> Unit,
  onContinue: () -> Unit,
  viewModel: RegisterViewModel = hiltViewModel()
) {
  // 👇 Mueve las variables aquí (fuera del Column)
  var firstName by rememberSaveable { mutableStateOf("") }
  var lastName by rememberSaveable { mutableStateOf("") }
  var dni by rememberSaveable { mutableStateOf("") }
  var license by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var phone by rememberSaveable { mutableStateOf("") }

  ScaffoldContainer(onBack = onBack, title = "Registro - Conductor") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Completa tus datos para crear tu cuenta",
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        DriverTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombres")
        DriverTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellidos")
        DriverTextField(value = dni, onValueChange = { dni = it }, label = "Número de DNI")
        DriverTextField(value = license, onValueChange = { license = it }, label = "Licencia de conducir")
        DriverTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
        DriverTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono de contacto")
      }

      FlotaPrimaryButton(
        text = "Continuar",
        onClick = {
          viewModel.registerDriver(
            firstName,
            lastName,
            dni,
            license,
            email,
            phone,
            password = "123456",
            onSuccess = onContinue,
            onError = { msg -> Log.e("Register", msg) }
          )
        },
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDriverRegistrationScreen() {
  FlotaTheme {
    DriverRegistrationScreen(onBack = {}, onContinue = {})
  }
}
