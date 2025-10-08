
package pe.edu.upc.flota365.presentation.ui.gestor

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
import pe.edu.upc.flota365.ui.theme.Flota365_App_mobileTheme

@Composable
fun ManagerRegistrationScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
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

                var firstName by rememberSaveable { mutableStateOf("") }
                var lastName by rememberSaveable { mutableStateOf("") }
                var ruc by rememberSaveable { mutableStateOf("") }
                var businessName by rememberSaveable { mutableStateOf("") }
                var email by rememberSaveable { mutableStateOf("") }
                var phone by rememberSaveable { mutableStateOf("") }

                ManagerTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombres")
                ManagerTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellidos")
                ManagerTextField(value = ruc, onValueChange = { ruc = it }, label = "Número de RUC")
                ManagerTextField(value = businessName, onValueChange = { businessName = it }, label = "Razón social")
                ManagerTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
                ManagerTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono de contacto")
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
    Flota365_App_mobileTheme {
        ManagerRegistrationScreen(
            onBack = {},
            onContinue = {}
        )
    }
}
