package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.features.manager.presentation.ui.SectionTitle

enum class DriverFormMode { Create, Edit }

@Composable
fun DriverFormScreen(
  mode: DriverFormMode,
  driverId: Int? = null,
  onCancel: () -> Unit = {},
  viewModel: DriverFormViewModel = hiltViewModel()
) {
  var firstName by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "Carlos" else "") }
  var lastName by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "Huamán" else "") }
  var phone by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "987654321" else "") }
  var email by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "carlos@example.com" else "") }
  var licenseNumber by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "A3B" else "") }

  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      SectionTitle(if (mode == DriverFormMode.Create) "Registrar nuevo conductor" else "Editar conductor")

      OutlinedTextField(firstName, { firstName = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
      OutlinedTextField(lastName, { lastName = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
      OutlinedTextField(phone, { phone = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
      OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
      OutlinedTextField(licenseNumber, { licenseNumber = it }, label = { Text("Licencia") }, modifier = Modifier.fillMaxWidth())

      Spacer(Modifier.height(16.dp))

      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ElevatedButton(
          onClick = {
            scope.launch {
              if (firstName.isBlank() || lastName.isBlank()) {
                snackbarHostState.showSnackbar("Completa los campos obligatorios")
                return@launch
              }

              if (mode == DriverFormMode.Create) {
                viewModel.createDriver(firstName, lastName, phone, email, licenseNumber) { success ->
                  scope.launch {
                    snackbarHostState.showSnackbar(
                      if (success) "Conductor registrado correctamente"
                      else "Error al registrar"
                    )
                  }
                }
              } else {
                driverId?.let {
                  viewModel.updateDriver(it, firstName, lastName, phone, email, licenseNumber) { success ->
                    scope.launch {
                      snackbarHostState.showSnackbar(
                        if (success) "Conductor actualizado"
                        else "Error al actualizar"
                      )
                    }
                  }
                }
              }
            }
          }
        ) {
          Text(if (mode == DriverFormMode.Create) "Registrar" else "Guardar cambios")
        }

        OutlinedButton(onClick = onCancel) { Text("Cancelar") }
      }
    }
  }
}
