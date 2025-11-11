package pe.edu.upc.flota365.features.manager.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.flota365.core.ui.theme.*
import pe.edu.upc.flota365.features.manager.presentation.viewmodel.ManagerViewModel
import pe.edu.upc.flota365.features.manager.presentation.event.ManagerUiEvent
import pe.edu.upc.flota365.features.manager.data.remote.models.ActiveVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.DashboardStatsDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
  viewModel: ManagerViewModel = hiltViewModel(),
  onMenuClick: () -> Unit = {},
  onNavigateToProfile: () -> Unit = {}
) {
  val uiState = viewModel.uiState

  LaunchedEffect(Unit) {
    viewModel.onEvent(ManagerUiEvent.LoadDashboard)
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Panel del Gestor de Flota",
            fontWeight = FontWeight.Bold,
            color = FlotaBgLight
          )
        },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(
              Icons.Filled.Menu,
              contentDescription = "Abrir menú",
              tint = FlotaBgLight
            )
          }
        },
        actions = {
          IconButton(onClick = onNavigateToProfile) {
            Icon(
              Icons.Filled.AccountCircle,
              contentDescription = "Ir al perfil",
              tint = FlotaBgLight
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FlotaPrimary)
      )
    },
    containerColor = FlotaGrayLight
  ) { padding ->
    Box(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
    ) {
      when {
        uiState.isLoading -> {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = FlotaPrimary
          )
        }

        uiState.error != null -> {
          Text(
            text = "Error: ${uiState.error}",
            color = FlotaError,
            modifier = Modifier.align(Alignment.Center)
          )
        }

        else -> {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            item { DashboardSummary(uiState.dashboardStats) }

            item {
              Text(
                "Vehículos Activos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = FlotaTextDark
              )
            }

            items(uiState.activeVehicles) { vehicle ->
              ActiveVehicleCard(vehicle)
            }
          }
        }
      }
    }
  }
}

@Composable
fun DashboardSummary(stats: DashboardStatsDto?) {
  if (stats == null) return

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      DashboardStatCard("Vehículos Totales", stats.totalVehicles, FlotaPrimary)
      DashboardStatCard("Vehículos Activos", stats.activeVehicles, FlotaSuccess)
    }
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      DashboardStatCard("Conductores", stats.totalDrivers, FlotaWarning)
      DashboardStatCard("Asignaciones Pendientes", stats.pendingAssignments, FlotaError)
    }
  }
}

@Composable
fun RowScope.DashboardStatCard(title: String, value: Int, color: androidx.compose.ui.graphics.Color) {
  Card(
    modifier = Modifier
      .weight(1f)
      .height(120.dp),
    colors = CardDefaults.cardColors(containerColor = color),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize()
    ) {
      Text(title, color = FlotaBgLight, fontWeight = FontWeight.Bold)
      Spacer(Modifier.height(8.dp))
      Text(
        value.toString(),
        color = FlotaBgLight,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

@Composable
fun ActiveVehicleCard(vehicle: ActiveVehicleDto) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = 100.dp),
    colors = CardDefaults.cardColors(containerColor = FlotaBgLight),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = vehicle.licensePlate,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = FlotaTextDark
      )
      Text(
        text = "${vehicle.brand ?: "Marca desconocida"} ${vehicle.model ?: ""}",
        style = MaterialTheme.typography.bodyMedium,
        color = FlotaTextDark
      )
      Text(
        text = "Conductor: ${vehicle.driverName ?: "No asignado"}",
        style = MaterialTheme.typography.bodySmall,
        color = FlotaTextDark
      )
      Text(
        text = "Flota: ${vehicle.fleetName ?: "N/A"}",
        style = MaterialTheme.typography.bodySmall,
        color = FlotaTextDark
      )
      Text(
        text = "Estado: ${vehicle.statusName ?: "Sin estado"}",
        style = MaterialTheme.typography.bodySmall,
        color = when (vehicle.statusName) {
          "Activo" -> FlotaSuccess
          "En mantenimiento" -> FlotaWarning
          else -> FlotaGrayMid
        },
        fontWeight = FontWeight.Medium
      )
    }
  }
}
