package pe.edu.upc.flota365.features.manager.data.remote.models

data class FleetSummaryDto(
  val totalFleets: Int,
  val primaryFleetVehicles: Int,
  val secondaryFleetVehicles: Int,
  val externalFleetVehicles: Int,

  val primaryFleetEfficiency: Double,
  val secondaryFleetEfficiency: Double,
  val externalFleetEfficiency: Double,
  val overallEfficiency: Double,

  val primaryFleetTrend: String?,
  val secondaryFleetTrend: String?,
  val externalFleetTrend: String?
)
