package pe.edu.upc.flota365.features.manager.presentation.state

import pe.edu.upc.flota365.features.manager.data.remote.models.*

data class ManagerUiState(
  val isLoading: Boolean = false,
  val error: String? = null,

  val dashboardStats: DashboardStatsDto? = null,
  val activeVehicles: List<ActiveVehicleDto> = emptyList(),
  val fleetSummary: List<FleetSummaryDto> = emptyList(),

  val fleets: List<FleetDto> = emptyList(),
  val vehicles: List<VehicleDto> = emptyList(),
  val drivers: List<DriverDto> = emptyList(),
  val driverStats: DriverStatsDto? = null,
  val assignments: List<Assignment> = emptyList(),
  val maintenanceRecords: List<MaintenanceRecordDto> = emptyList(),
  val overdueMaintenance: List<MaintenanceRecordDto> = emptyList(),
  val reports: List<Report> = emptyList()
)
