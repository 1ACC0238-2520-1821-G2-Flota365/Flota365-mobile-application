package pe.edu.upc.flota365.features.manager.presentation.report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateReportRequest
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateReportScreen(
  onReportCreated: () -> Unit = {},
  onCancel: () -> Unit = {},
  viewModel: ReportsViewModel = hiltViewModel()
) {
  var title by remember { mutableStateOf("") }
  var type by remember { mutableStateOf("") }
  var createdBy by remember { mutableStateOf("Administrador") }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  AppScaffold(
    title = "Nuevo Reporte",
    onMenuClick = onCancel,
    snackbarHostState = snackbarHostState
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text("Crear nuevo reporte", style = MaterialTheme.typography.headlineSmall)

      OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Título del reporte") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )

      OutlinedTextField(
        value = type,
        onValueChange = { type = it },
        label = { Text("Tipo de reporte") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )

      OutlinedTextField(
        value = createdBy,
        onValueChange = { createdBy = it },
        label = { Text("Creado por") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )

      Spacer(Modifier.height(16.dp))

      Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        OutlinedButton(onClick = onCancel) {
          Text("Cancelar")
        }

        Spacer(Modifier.width(8.dp))

        Button(onClick = {
          scope.launch {
            if (title.isBlank() || type.isBlank()) {
              snackbarHostState.showSnackbar("Completa todos los campos")
              return@launch
            }

            val request = CreateReportRequest(
              title = title,
              type = type,
              generatedAt = Instant.now().toString(),
              createdBy = createdBy
            )

            val result = viewModel.createReport(request)
            when (result) {
              is Resource.Success -> {
                snackbarHostState.showSnackbar("Reporte creado correctamente")
                onReportCreated()
              }
              is Resource.Error -> snackbarHostState.showSnackbar(result.message ?: "Error al crear reporte")
              else -> snackbarHostState.showSnackbar("Error desconocido")
            }
          }
        }) {
          Text("Guardar reporte")
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreateReportScreenPreview() {
  FlotaTheme {
    CreateReportScreen()
  }
}
