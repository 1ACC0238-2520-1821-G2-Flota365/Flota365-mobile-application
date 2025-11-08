package pe.edu.upc.flota365.features.manager.data.remote.models

data class CreateMaintenanceRecordRequest(
  val vehicleId: Int,
  val serviceType: String,
  val date: String,
  val cost: Double
)
