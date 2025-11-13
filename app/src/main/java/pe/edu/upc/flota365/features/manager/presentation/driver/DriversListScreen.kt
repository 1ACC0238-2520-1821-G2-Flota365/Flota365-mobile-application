package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversListScreen(
  viewModel: DriversViewModel = hiltViewModel(),
  onMenuClick: () -> Unit = {},
  onCreate: () -> Unit = {},
  onEdit: () -> Unit = {},
  onStats: () -> Unit = {}
) {
  val drivers = viewModel.drivers
  val stats = viewModel.stats
  val isLoading = viewModel.isLoading
  val error = viewModel.error

  LaunchedEffect(Unit) {
    viewModel.loadDrivers()
    viewModel.loadDriverStats()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Conductores") },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(Icons.Default.Menu, contentDescription = "Menú")
          }
        }
      )
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // --- BOTONES ---
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ElevatedButton(onClick = onCreate, modifier = Modifier.weight(1f)) {
          Icon(Icons.Default.Add, contentDescription = null)
          Spacer(Modifier.width(8.dp))
          Text("Nuevo")
        }

        ElevatedButton(onClick = onStats, modifier = Modifier.weight(1f)) {
          Icon(Icons.Default.BarChart, contentDescription = null)
          Spacer(Modifier.width(8.dp))
          Text("Estadísticas")
        }
      }

      Divider(Modifier.padding(vertical = 8.dp))

      // --- CONTENIDO ---
      when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
        drivers.isEmpty() -> Text("No hay conductores registrados.")
        else -> {
          stats?.let {
            Text(
              "Conductores activos: ${it.activeDrivers}/${it.totalDrivers} | Experiencia promedio: ${it.averageExperience} años",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.primary
            )
          }

          drivers.forEach { driver ->
            ElevatedCard(Modifier.fillMaxWidth()) {
              Column(Modifier.padding(12.dp)) {
                Text(
                  "👤 ${driver.firstName} ${driver.lastName}",
                  style = MaterialTheme.typography.titleMedium
                )
                Text("📞 Teléfono: ${driver.phone}")
                Text("🚗 Licencia: ${driver.licenseNumber}")
                Text(
                  if (driver.isActive) "✅ Activo" else "❌ Inactivo",
                  color = if (driver.isActive) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                )
                Spacer(Modifier.height(6.dp))
                Row(
                  Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.End
                ) {
                  TextButton(onClick = { onEdit() }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Text("Editar")
                  }
                  TextButton(onClick = { viewModel.deleteDriver(driver.id) }) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    Text("Eliminar", color = Color.Red)
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
