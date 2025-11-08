package pe.edu.upc.flota365.features.manager.data.remote.models

data class ActiveVehicleDto(
  val id: Int,
  val licensePlate: String,
  val driverName: String?,
  val status: String,
  val currentAssignmentId: Int?
)
