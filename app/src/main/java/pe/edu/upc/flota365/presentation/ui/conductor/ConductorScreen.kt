package pe.edu.upc.flota365.presentation.ui.conductor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.login.FlotaPrimaryButton
import pe.edu.upc.flota365.presentation.ui.login.ScaffoldContainer
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.ui.theme.FlotaTheme

@Composable
fun DriverRegistrationScreen(
  onBack: () -> Unit,
  onContinue: () -> Unit
) {
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

        var firstName by rememberSaveable { mutableStateOf("") }
        var lastName by rememberSaveable { mutableStateOf("") }
        var dni by rememberSaveable { mutableStateOf("") }
        var license by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var phone by rememberSaveable { mutableStateOf("") }

        DriverTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombres")
        DriverTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellidos")
        DriverTextField(value = dni, onValueChange = { dni = it }, label = "Número de DNI")
        DriverTextField(value = license, onValueChange = { license = it }, label = "Licencia de conducir")
        DriverTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
        DriverTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono de contacto")
      }

      FlotaPrimaryButton(
        text = "Continuar",
        onClick = onContinue,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun DriverTextField(
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
  FlotaTheme() {
    DriverRegistrationScreen(
      onBack = {},
      onContinue = {}
    )
  }
}

@Composable
private fun DriverTextField(
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
    Flota365_App_mobileTheme {
        DriverRegistrationScreen(
            onBack = {},
            onContinue = {}
        )
    }
}