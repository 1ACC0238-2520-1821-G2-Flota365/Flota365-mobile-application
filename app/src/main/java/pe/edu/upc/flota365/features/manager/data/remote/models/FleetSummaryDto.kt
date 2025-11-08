package pe.edu.upc.flota365.features.manager.data.remote.models

data class FleetSummaryDto(
  val totalFleets: Int,
  val totalVehicles: Int,
  val totalDrivers: Int,
  val averageUtilization: Double
)
