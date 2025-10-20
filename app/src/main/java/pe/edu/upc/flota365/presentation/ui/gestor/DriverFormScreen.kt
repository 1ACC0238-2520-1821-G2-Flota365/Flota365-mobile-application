package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.components.SectionTitle
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

enum class DriverFormMode { Create, Edit }

@Composable
fun DriverFormScreen(mode: DriverFormMode) {
  var nombre by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "Carlos Huamán" else "") }
  var dni by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "70654321" else "") }
  var licencia by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "A3B" else "") }
  var telefono by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "987654321" else "") }
  var direccion by remember { mutableStateOf(if (mode == DriverFormMode.Edit) "Av. Siempre Viva 123" else "") }

  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    SectionTitle(if (mode == DriverFormMode.Create) "Registrar nuevo conductor" else "Editar conductor")
    OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombres y apellidos") }, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(dni, { dni = it }, label = { Text("DNI") }, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(licencia, { licencia = it }, label = { Text("Licencia") }, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(telefono, { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(direccion, { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      ElevatedButton(onClick = { }) { Text(if (mode == DriverFormMode.Create) "Registrar" else "Guardar cambios") }
      OutlinedButton(onClick = { }) { Text("Cancelar") }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DriverFormCreatePreview() {
  FlotaTheme { DriverFormScreen(DriverFormMode.Create) }
}

@Preview(showBackground = true)
@Composable
private fun DriverFormEditPreview() {
  FlotaTheme { DriverFormScreen(DriverFormMode.Edit) }
}
