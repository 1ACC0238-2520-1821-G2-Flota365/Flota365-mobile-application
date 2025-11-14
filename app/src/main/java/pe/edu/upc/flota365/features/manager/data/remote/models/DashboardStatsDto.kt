package pe.edu.upc.flota365.features.manager.data.remote.models

data class DashboardStatsDto(
  val totalVehicles: Int,
  val activeDrivers: Int,
  val vehiclesInMaintenance: Int,
  val fleetEfficiency: Double,
  val totalVehiclesChange: String,
  val activeDriversChange: String,
  val maintenanceChange: String,
  val efficiencyChange: String,
  val lastUpdated: String,
  val totalFleets: Int,
  val alertsCount: Int,
  val averageVehicleAge: Double,
  val vehiclesDueForService: Int
)
