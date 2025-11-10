package pe.edu.upc.flota365.features.manager.presentation.monitoring

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.manager.data.remote.models.ActiveVehicleDto
import pe.edu.upc.flota365.features.manager.presentation.event.ManagerUiEvent
import pe.edu.upc.flota365.features.manager.presentation.viewmodel.ManagerViewModel

/* =======================
   Model
   ======================= */
data class MonitoringVehicleUi(
  val plate: String,
  val model: String,
  val driver: String,
  val status: String = "Activo",
  @DrawableRes val mapImage: Int? = null
)

/* =======================
   Screen
   ======================= */
@Composable
fun MonitoringScreen(
  viewModel: ManagerViewModel,
  onMenuClick: () -> Unit = {}
) {
  val state = viewModel.uiState

  // 🔹 Cargar datos del backend al entrar a la pantalla
  LaunchedEffect(Unit) {
    viewModel.onEvent(ManagerUiEvent.LoadDashboard)
  }

  AppScaffold(
    title = "Monitoreo",
    onMenuClick = onMenuClick
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

      // 🗺️ Mapa o placeholder
      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(12.dp)) {
          MapPreview(
            imageRes = null,
            modifier = Modifier
              .fillMaxWidth()
              .height(220.dp)
          )
          Spacer(Modifier.height(8.dp))
          Text(
            "Estado de vehículos:",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // 🔹 Mostrar estado de carga, error o datos
      when {
        state.isLoading -> {
          Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
          }
        }

        state.error != null -> {
          Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
        }

        state.activeVehicles.isNotEmpty() -> {
          // 🔹 Lista real de vehículos del backend
          ElevatedCard(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
              state.activeVehicles.forEachIndexed { index, v ->
                VehicleMonitorRow(
                  plate = "Placa: ${v.licensePlate}",
                  model = "Modelo: ${v.brand ?: "N/A"} ${v.model ?: ""}",
                  driver = "Conductor: ${v.driverName ?: "No asignado"}",
                  status = v.statusName ?: "Desconocido",
                  onMore = { /* acciones futuras */ }
                )
                if (index != state.activeVehicles.lastIndex) Divider()
              }
            }
          }
        }

        else -> {
          Text("No hay vehículos activos en este momento.")
        }
      }
    }
  }
}

/* =======================
   Building blocks
   ======================= */

@Composable
private fun MapPreview(
  @DrawableRes imageRes: Int?,
  modifier: Modifier = Modifier
) {
  if (imageRes != null) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = "Mapa",
      modifier = modifier.clip(RoundedCornerShape(12.dp))
    )
  } else {
    Box(
      modifier
        .clip(RoundedCornerShape(12.dp))
        .background(
          Brush.linearGradient(
            listOf(
              MaterialTheme.colorScheme.primary.copy(alpha = .20f),
              MaterialTheme.colorScheme.secondary.copy(alpha = .20f)
            )
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Text("Mapa (visual)", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}

@Composable
private fun VehicleMonitorRow(
  plate: String,
  model: String,
  driver: String,
  status: String,
  onMore: () -> Unit
) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 6.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // 🚗 Ícono del vehículo
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

    // 📋 Texto + estado
    Column(Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(plate, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(8.dp))
        StatusPill(status)
      }
      Text(model, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(driver, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }

    // ⋮ Menú contextual
    var expanded by remember { mutableStateOf(false) }
    Box {
      IconButton(onClick = { expanded = true }) {
        Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones")
      }
      DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text("Ver detalles") }, onClick = { expanded = false })
        DropdownMenuItem(text = { Text("Reportar incidencia") }, onClick = { expanded = false })
      }
    }
  }
}

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

/* =======================
   Preview
   ======================= */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MonitoringScreenPreview() {
  FlotaTheme {
    // En el preview no se pasa un ViewModel real
    // MonitoringScreen(viewModel = FakeViewModel())
    Text("Vista previa de Monitoreo")
  }
}
