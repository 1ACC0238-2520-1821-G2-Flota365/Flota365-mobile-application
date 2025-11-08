package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversListScreen(
  onCreate: () -> Unit,
  onEdit: () -> Unit,
  onStats: () -> Unit,
  onMenuClick: () -> Unit
) {
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
      ElevatedButton(
        onClick = onCreate,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(Icons.Default.Add, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text("Nuevo Conductor")
      }

      ElevatedButton(
        onClick = onEdit,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(Icons.Default.Edit, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text("Editar Conductor")
      }

      ElevatedButton(
        onClick = onStats,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(Icons.Default.BarChart, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text("Ver Estadísticas")
      }
    }
  }
}
