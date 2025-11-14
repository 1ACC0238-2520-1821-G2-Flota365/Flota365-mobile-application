package pe.edu.upc.flota365.features.manager.data.remote.models

data class DashboardStatsDto(
  val totalVehicles: Int,
  val activeVehicles: Int,
  val totalDrivers: Int,
  val totalAssignments: Int,
  val completedAssignments: Int,
  val pendingAssignments: Int
)
