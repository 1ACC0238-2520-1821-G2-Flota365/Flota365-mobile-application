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
import pe.edu.upc.flota365.features.manager.data.remote.models.FleetSummaryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
  viewModel: ManagerViewModel = hiltViewModel(),
  onNavigateToProfile: () -> Unit = {}
) {
  val uiState = viewModel.uiState

  // Cargar dashboard al entrar
  LaunchedEffect(Unit) {
    viewModel.onEvent(ManagerUiEvent.LoadDashboard)
  }

  var showLoader by remember { mutableStateOf(true) }

  // Cuando llega cualquier dato o error → ocultar loader
  LaunchedEffect(
    uiState.dashboardStats,
    uiState.activeVehicles,
    uiState.fleetSummary,
    uiState.error
  ) {
    if (
      uiState.dashboardStats != null ||
      uiState.activeVehicles.isNotEmpty() ||
      uiState.fleetSummary != null ||
      uiState.error != null
    ) {
      showLoader = false
    }
  }

  // Loader Fullscreen
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

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text("Panel del Gestor", fontWeight = FontWeight.SemiBold)
        },
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

      // --- Dashboard stats generales ---
      DashboardSummary(uiState.dashboardStats)

      Spacer(Modifier.height(16.dp))

      // --- NUEVO: resumen de flotas ---
      uiState.fleetSummary?.let {
        FleetSummarySection(it)
      }

      Spacer(Modifier.height(16.dp))

      Text(
        "Vehículos activos",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = FlotaTextDark
      )

      Spacer(Modifier.height(8.dp))

      if (uiState.error != null) {
        Box(
          modifier = Modifier.fillMaxWidth().weight(1f),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Error: ${uiState.error}",
            color = FlotaError
          )
        }
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxWidth().weight(1f),
          verticalArrangement = Arrangement.spacedBy(16.dp),
          contentPadding = PaddingValues(bottom = 12.dp)
        ) {
          items(uiState.activeVehicles) {
            ActiveVehicleCard(it)
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
      DashboardStatCard("Conductores activos", stats.activeDrivers, FlotaSuccess)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      DashboardStatCard("En mantenimiento", stats.vehiclesInMaintenance, FlotaWarning)
      DashboardStatCard("Alertas", stats.alertsCount, FlotaError)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      DashboardStatCard("Total Flotas", stats.totalFleets, FlotaPrimary)
      DashboardStatCard("Próx. Servicio", stats.vehiclesDueForService, FlotaSuccess)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      DashboardStatCard("Edad Promedio", stats.averageVehicleAge.toInt(), FlotaWarning)
      DashboardStatCard("Eficiencia %", stats.fleetEfficiency.toInt(), FlotaPrimary)
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
@Composable
fun FleetSummarySection(summary: FleetSummaryDto) {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

    Text(
      "Resumen de Flotas",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      color = FlotaTextDark
    )

    FleetCard(
      title = "Flota Primaria",
      vehicles = summary.primaryFleetVehicles,
      efficiency = summary.primaryFleetEfficiency,
      trend = summary.primaryFleetTrend
    )

    FleetCard(
      title = "Flota Secundaria",
      vehicles = summary.secondaryFleetVehicles,
      efficiency = summary.secondaryFleetEfficiency,
      trend = summary.secondaryFleetTrend
    )

    FleetCard(
      title = "Flota Externa",
      vehicles = summary.externalFleetVehicles,
      efficiency = summary.externalFleetEfficiency,
      trend = summary.externalFleetTrend
    )

    Spacer(Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = FlotaBgLight),
      shape = RoundedCornerShape(14.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(Modifier.padding(16.dp)) {
        Text(
          "Eficiencia General",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Text("${summary.overallEfficiency} %")
      }
    }
  }
}

@Composable
fun FleetCard(
  title: String,
  vehicles: Int,
  efficiency: Double,
  trend: String?
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = FlotaBgLight),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(Modifier.padding(16.dp)) {
      Text(
        title,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
      )
      Text("Vehículos: $vehicles")
      Text("Eficiencia: $efficiency%")

      val trendText = when (trend) {
        "improving" -> "Mejorando"
        "stable" -> "Estable"
        "needs_improvement" -> "Necesita mejora"
        else -> trend
      }

      val trendColor = when (trend) {
        "improving" -> FlotaSuccess
        "stable" -> FlotaWarning
        "needs_improvement" -> FlotaError
        else -> FlotaGrayMid
      }

      Text(
        "Tendencia: $trendText",
        color = trendColor,
        fontWeight = FontWeight.SemiBold
      )
    }
  }
}
