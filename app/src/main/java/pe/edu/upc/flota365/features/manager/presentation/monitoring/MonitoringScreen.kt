package pe.edu.upc.flota365.features.manager.presentation.monitoring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.manager.data.remote.models.ActiveVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateReportRequest
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold
import pe.edu.upc.flota365.core.utils.Resource

@Composable
fun MonitoringScreen(
  viewModel: MonitoringViewModel = hiltViewModel(),
  onMenuClick: () -> Unit = {}
) {
  val vehicles by viewModel.activeVehicles
  val isLoading by viewModel.isLoading
  val error by viewModel.error

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  var showDetailDialog by remember { mutableStateOf<ActiveVehicleDto?>(null) }
  var showReportDialog by remember { mutableStateOf<ActiveVehicleDto?>(null) }
  var showDeleteConfirm by remember { mutableStateOf<ActiveVehicleDto?>(null) }


  LaunchedEffect(Unit) {
    viewModel.loadActiveVehicles()
  }

  AppScaffold(
    title = "Monitoreo",
    onMenuClick = onMenuClick,
    snackbarHostState = snackbarHostState
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

      Text(
        "Monitoreo de Flota:",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
      )

      when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }

        error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)

        vehicles.isEmpty() -> Text("No hay vehículos activos en este momento.")

        else -> {
          ElevatedCard(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
              vehicles.forEachIndexed { index, v ->
                VehicleMonitorRow(
                  vehicle = v,
                  onDetails = { showDetailDialog = v },
                  onReport = { showReportDialog = v },
                  onDelete = { showDeleteConfirm = v }
                )
                if (index != vehicles.lastIndex) Divider()
              }
            }
          }
        }
      }
    }
  }

  /* ---------------------- Diálogo de Detalles ---------------------- */
  showDetailDialog?.let { vehicle ->
    AlertDialog(
      onDismissRequest = { showDetailDialog = null },
      title = { Text("Detalles del Vehículo") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text("Placa: ${vehicle.licensePlate}")
          Text("Modelo: ${vehicle.brand ?: "-"} ${vehicle.model ?: "-"}")
          Text("Conductor: ${vehicle.driverName ?: "No asignado"}")
          Text("Estado: ${vehicle.statusName ?: "-"}")
          Text("Último servicio: ${vehicle.lastServiceDate ?: "N/A"}")
        }
      },
      confirmButton = { Button(onClick = { showDetailDialog = null }) { Text("Cerrar") } }
    )
  }

  /* ---------------------- Diálogo Reportar Incidencia ---------------------- */
  showReportDialog?.let { vehicle ->
    var desc by remember { mutableStateOf("") }

    AlertDialog(
      onDismissRequest = { showReportDialog = null },
      title = { Text("Reportar Incidencia") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("Vehículo: ${vehicle.licensePlate}")
          OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Descripción de la incidencia") },
            minLines = 3
          )
        }
      },
      confirmButton = {
        Button(onClick = {
          scope.launch {
            val result = viewModel.createReport(
              CreateReportRequest(
                title = "Incidencia en ${vehicle.licensePlate}",
                type = "Vehículo",
                description = desc,
                generatedAt = "",
                createdBy = "Administrador"
              )
            )
            if (result is Resource.Success)
              snackbarHostState.showSnackbar("Incidencia reportada correctamente")
            else snackbarHostState.showSnackbar("Error al reportar incidencia")

            showReportDialog = null
          }
        }) { Text("Enviar") }
      },
      dismissButton = {
        OutlinedButton(onClick = { showReportDialog = null }) { Text("Cancelar") }
      }
    )
  }

  /* ---------------------- Confirmar Eliminación ---------------------- */
  showDeleteConfirm?.let { vehicle ->
    AlertDialog(
      onDismissRequest = { showDeleteConfirm = null },
      title = { Text("Eliminar Vehículo") },
      text = { Text("¿Seguro que deseas eliminar el vehículo ${vehicle.licensePlate}? Esta acción no se puede deshacer.") },
      confirmButton = {
        Button(
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
          onClick = {
            scope.launch {
              val result = viewModel.deleteVehicle(vehicle.id)
              if (result is Resource.Success)
                snackbarHostState.showSnackbar("Vehículo eliminado correctamente")
              else snackbarHostState.showSnackbar("Error al eliminar vehículo")
              viewModel.loadActiveVehicles()
              showDeleteConfirm = null
            }
          }
        ) {
          Text("Eliminar")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { showDeleteConfirm = null }) { Text("Cancelar") }
      }
    )
  }
}

/* =======================
   Composable de Fila Vehículo
   ======================= */
@Composable
private fun VehicleMonitorRow(
  vehicle: ActiveVehicleDto,
  onDetails: () -> Unit,
  onReport: () -> Unit,
  onDelete: () -> Unit
) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 6.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      tonalElevation = 2.dp,
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colorScheme.primary.copy(alpha = .12f)
    ) {
      Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.Filled.DirectionsCar,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary
        )
      }
    }

    Spacer(Modifier.width(12.dp))

    Column(Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Placa: ${vehicle.licensePlate}", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(8.dp))
        StatusPill(vehicle.statusName ?: "-")
      }
      Text("${vehicle.brand ?: "-"} ${vehicle.model ?: ""}", style = MaterialTheme.typography.bodySmall)
      Text("Conductor: ${vehicle.driverName ?: "No asignado"}", style = MaterialTheme.typography.bodySmall)
    }

    // ⋮ Menú contextual
    var expanded by remember { mutableStateOf(false) }
    Box {
      IconButton(onClick = { expanded = true }) {
        Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones")
      }
      DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text("Ver detalles") }, onClick = {
          expanded = false; onDetails()
        })
        DropdownMenuItem(text = { Text("Reportar incidencia") }, onClick = {
          expanded = false; onReport()
        })
        DropdownMenuItem(
          text = { Text("Eliminar", color = Color(0xFFD32F2F)) },
          onClick = {
            expanded = false; onDelete()
          },
          leadingIcon = {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F))
          }
        )
      }
    }
  }
}

/* =======================
   Estado visual
   ======================= */
@Composable
private fun StatusPill(label: String) {
  AssistChip(
    onClick = {},
    label = { Text(label) },
    enabled = false,
    colors = AssistChipDefaults.assistChipColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      labelColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
  )
}
