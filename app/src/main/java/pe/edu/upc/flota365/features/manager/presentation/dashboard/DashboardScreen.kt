package pe.edu.upc.flota365.features.manager.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
  onNavigateToProfile: () -> Unit = {}
) {
  val uiState = viewModel.uiState

  // 1️⃣ Disparamos la carga del dashboard al entrar
  LaunchedEffect(Unit) {
    viewModel.onEvent(ManagerUiEvent.LoadDashboard)
  }

  // 2️⃣ Loader de inicio mientras el back responde (sin usar uiState.isLoading)
  var showLoader by remember { mutableStateOf(true) }

  LaunchedEffect(
    uiState.dashboardStats,
    uiState.activeVehicles,
    uiState.error
  ) {
    // Cuando ya hay stats, vehículos o error, dejamos de mostrar el loader
    if (uiState.dashboardStats != null ||
      uiState.activeVehicles.isNotEmpty() ||
      uiState.error != null
    ) {
      showLoader = false
    }
  }

  // 🔵 PANTALLA DE CARGA COMPLETA MIENTRAS EL BACK RESPONDE
  if (showLoader) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(FlotaGrayLight),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        color = FlotaPrimary,
        strokeWidth = 5.dp
      )
    }
    return
  }

  // 🔵 DASHBOARD NORMAL CUANDO YA HAY RESPUESTA
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Panel del Gestor",
            fontWeight = FontWeight.SemiBold
          )
        },
        navigationIcon = {},
        actions = {
          IconButton(onClick = onNavigateToProfile) {
            Icon(
              imageVector = Icons.Filled.AccountCircle,
              contentDescription = "Perfil",
              tint = FlotaBgLight
            )
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = FlotaPrimary,
          titleContentColor = FlotaBgLight,
          actionIconContentColor = FlotaBgLight
        )
      )
    },
    containerColor = FlotaGrayLight
  ) { padding ->

    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            listOf(
              FlotaPrimary.copy(alpha = 0.03f),
              FlotaGrayLight
            )
          )
        )
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

      // 🔹 Resumen (4 tarjetas) fijo arriba
      DashboardSummary(uiState.dashboardStats)

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        "Vehículos activos",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = FlotaTextDark
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Si hay error, lo mostramos en el área de la lista
      if (uiState.error != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Error: ${uiState.error}",
            color = FlotaError
          )
        }
      } else {
        // 🔹 SOLO la lista scrollea
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          verticalArrangement = Arrangement.spacedBy(16.dp),
          contentPadding = PaddingValues(bottom = 12.dp)
        ) {
          items(uiState.activeVehicles) { vehicle ->
            ActiveVehicleCard(vehicle)
          }
        }
      }
    }
  }
}

/* ---------------------- RESUMEN Y TARJETAS ---------------------- */

@Composable
fun DashboardSummary(stats: DashboardStatsDto?) {
  if (stats == null) return

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      DashboardStatCard("Vehículos totales", stats.totalVehicles, FlotaPrimary)
      DashboardStatCard("Vehículos activos", stats.activeVehicles, FlotaSuccess)
    }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      DashboardStatCard("Conductores", stats.totalDrivers, FlotaWarning)
      DashboardStatCard("Asignaciones pendientes", stats.pendingAssignments, FlotaError)
    }
  }
}

@Composable
fun RowScope.DashboardStatCard(
  title: String,
  value: Int,
  color: androidx.compose.ui.graphics.Color
) {
  Card(
    modifier = Modifier
      .weight(1f)
      .height(110.dp),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = color)
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(title, color = FlotaBgLight, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        value.toString(),
        color = FlotaBgLight,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold
      )
    }
  }
}

@Composable
fun ActiveVehicleCard(vehicle: ActiveVehicleDto) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = FlotaBgLight)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        vehicle.licensePlate,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
      )
      Text("${vehicle.brand ?: ""} ${vehicle.model ?: ""}")
      Text("Conductor: ${vehicle.driverName ?: "No asignado"}")
      Text("Flota: ${vehicle.fleetName ?: "N/A"}")
      Text(
        "Estado: ${vehicle.statusName ?: "Sin estado"}",
        color = when (vehicle.statusName) {
          "Activo" -> FlotaSuccess
          "En mantenimiento" -> FlotaWarning
          else -> FlotaGrayMid
        }
      )
    }
  }
}
